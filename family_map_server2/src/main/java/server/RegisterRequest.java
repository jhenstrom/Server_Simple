package server;

/** Object used to perform a register request in the Facade*/
public class RegisterRequest
{
  /** user's unique username*/
  private String userName;
  /** user's password*/
  private String password;
  /** user's email address*/
  private String email;
  /** user's first name*/
  private String firstName;
  /** user's last name*/
  private String lastName;
  /** user's gender*/
  private String gender;

  /**
  * Constructer creating RegisterRequest object
  * @param userName user's unique username
  * @param password user's password
  * @param email user's email address
  * @param firstName user's first name
  * @param lastName user's last name
  * @param gender user's gender
  */
  RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender)
  {
    this.userName = userName;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
  }

  public String getUserName(){ return userName; }

  public String getPassword(){return password; }

  public String getEmail(){ return email; }

  public String getFirstName(){ return firstName; }

  public String getLastName(){ return lastName; }

  public String getGender(){ return gender; }

  public boolean valid()
  {
    if(userName.equals("") || password.equals("") || email.equals("") || firstName.equals("")
            || lastName.equals("") || gender.equals(""))
      return false;
    else if (!gender.equals("f") && !gender.equals("m")) return false;
    else if (userName == null || password == null || email == null || firstName == null
            || lastName == null || gender == null)
      return false;
    else return true;
  }
}
