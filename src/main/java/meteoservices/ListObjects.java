package meteoservices;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ListObjects implements RequestHandler<RequestBucket, List<ResponseKey>> {
  public List<ResponseKey> handleRequest(RequestBucket req, Context context) {
    String clientRegion = "us-east-1";
    String bucketName = req.bucket;
    List<ResponseKey> res = new ArrayList<ResponseKey>();
      
    try {    
      AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
              .withRegion(clientRegion)
              .build();
    
      System.out.println("Listing objects");
    
      ListObjectsV2Request lov2req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
      ListObjectsV2Result result;
      ResponseKey resItem;

      do {
        result = s3Client.listObjectsV2(lov2req);
  
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
          System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
          resItem = new ResponseKey();
          resItem.key = objectSummary.getKey();
          resItem.size = objectSummary.getSize();
          res.add(resItem);
        }
        // If there are more than maxKeys keys in the bucket, get a continuation token
        // and list the next objects.
        String token = result.getNextContinuationToken();
        System.out.println("Next Continuation Token: " + token);
        lov2req.setContinuationToken(token);
      } while (result.isTruncated());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    return res;  
  }
}
