package server;

/** Result of a login request in Facade*/
public class LoginResponse
{
  /** token from AuthToken of user*/
  private String token;
  /** username of user*/
  private String userName;
  /** unique personID of user*/
  private String personID;

  /**
  * Contructor creating LoginResponse object
  * @param token token from AuthToken of user
  * @param userName username of user
  * @param personID unique personID of user
  */
  LoginResponse(String token, String userName, String personID)
  {
    this.token = token;
    this.userName = userName;
    this.personID = personID;
  }

  public String getToken(){ return token; }

  public String getUserName(){ return userName; }

  public String getPersonID(){return personID; }
}
