package model;

public class Message {
  private int userId;
  private String originTitle;
  private String originContent;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getOriginTitle() {
    return originTitle;
  }

  public void setOriginTitle(String originTitle) {
    this.originTitle = originTitle;
  }

  public String getOriginContent() {
    return originContent;
  }

  public void setOriginContent(String originContent) {
    this.originContent = originContent;
  }

  public Message(int userId, String originTitle, String originContent) {

    this.userId = userId;
    this.originTitle = originTitle;
    this.originContent = originContent;
  }
}
