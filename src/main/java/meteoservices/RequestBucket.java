package meteoservices;

public class RequestBucket {
  String bucket;
  String parameter;
  String level;
  String date;
  String hour;

  public String getBucket(){
    return bucket;
  }

  public void setBucket(String bucket){
    this.bucket = bucket;
  }

  public String getParameter(){
    return parameter;
  }

  public void setParameter(String parameter){
    this.parameter = parameter;
  }

  public String getLevel(){
    return level;
  }

  public void setLevel(String level){
    this.level = level;
  }

  public String getDate(){
    return date;
  }

  public void setDate(String date){
    this.date = date;
  }

  public String getHour(){
    return hour;
  }

  public void setHour(String hour){
    this.hour = hour;
  }
}