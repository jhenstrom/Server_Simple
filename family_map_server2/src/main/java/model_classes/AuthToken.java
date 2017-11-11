package model_classes;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import data_access_classes.AuthTokenAccess;

/** Stores the Authentication Token assign to User on Login to session*/
public class AuthToken
{
  /** Unique token for User Session*/
  private String token;
  /** userName of who token belongs to*/
  private String userName;
  /** personID of user*/
  private String personID;

  /**
  * Constructer to create an AuthToken object
  * @param userName userName of who the AuthToken belongs to
  * @param personID personID of user
  */
  public AuthToken(String userName, String personID)
  {
    this.userName = userName;
    this.personID = personID;
    this.token = generateToken();

  }

  /**
   * Constructer to create an AuthToken object
   * @param token Token for this AuthToken
   * @param userName userName of who the AuthToken belongs to
   * @param personID personID of user
   */
  public AuthToken(String token, String userName, String personID)
  {
    this.userName = userName;
    this.personID = personID;
    this.token = token;

  }

  public String getToken(){ return token; }

  public String getUserName(){ return userName; }

  public String getPersonID(){ return personID; }

  /**
  * Method to create string obj of AuthToken
  * @return String of AuthToken
  */
  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("Authorization Token: " + token + "\n");
    s.append("User Name: " + userName + "\n");
    s.append("Person ID: " + personID + "\n");
    return s.toString();
  }

  /**
   * generates a unique 8 digit token
   * @return 8 char long string that is unique
   */
  private String generateToken()
  {
    return UUID.randomUUID().toString();
  }
}
