package server;

/** Object used to perform a login request in Facade*/
public class LoginRequest
{
  /** username of person logging in*/
  private String userName;
  /** password of person logging in*/
  private String password;

  /**
  * Contructor creating LoginRequest object
  * @param userName username of person logging in
  * @param password password of person logging in
  */
  LoginRequest(String userName, String password)
  {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName(){ return userName; }

  public String getPassword(){ return password; }

  public boolean valid()
  {
    return (!userName.equals("") && !password.equals("") && userName != null && password != null);
  }
}
