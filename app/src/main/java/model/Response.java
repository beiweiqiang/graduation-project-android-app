package model;

/**
 * Created by beiweiqiang on 4/7/18.
 */

public class Response {
  private int code;
  private String msg;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Response(int code, String msg) {

    this.code = code;
    this.msg = msg;
  }
}
