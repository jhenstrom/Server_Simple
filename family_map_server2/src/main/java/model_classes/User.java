package model_classes;

import java.util.UUID;

/** Stores information about a User*/
public class User
{
  /** Unique User's username*/
  private String userName;
  /** User's password*/
  private String password;
  /** User's email address*/
  private String email;
  /** User's first name*/
  private String firstName;
  /** User's last name*/
  private String lastName;
  /** User's gender*/
  private String gender;
  /** User's person object's unique idenitifier*/
  private String personID;

  /**
  * Contructor to create User object
  * @param userName Unique User's username
  * @param password User's password
  * @param email User's email address
  * @param firstName User's first name
  * @param lastName User's last name
  * @param gender User's gender
  */
  public User(String userName, String password, String email, String firstName, String lastName, String gender)
  {
    this.userName = userName;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.personID = generatePersonID();
  }

  /**
   * Contructor to create User object
   * @param userName Unique User's username
   * @param password User's password
   * @param email User's email address
   * @param firstName User's first name
   * @param lastName User's last name
   * @param gender User's gender
   * @param personID User's person ID
   */
  public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID)
  {
    this.userName = userName;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.personID = personID;
  }


  public String getUserName(){ return userName;}

  public String getPassword(){ return password; }

  public String getEmail(){ return email; }

  public String getFirstName(){ return firstName; }

  public String getLastName(){return lastName; }

  public String getGender(){ return gender; }

  public String getPersonID(){ return personID; }

  /**
  * Method to create string obj of User
  * @return String of User
  */
  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("UserName: " + userName + "\n");
    s.append("Password: " + password + "\n");
    s.append("Email: " + email + "\n");
    s.append("Name: " + firstName + " " + lastName + "\n");
    s.append("Gender: " + gender + "\n");
    s.append("Person ID: " + personID + "\n");
    return s.toString();

  }

  /**
   * Method to check if a given object is the same as THIS user
   * @param o object we are checking for equals
   * @return true if the User's are equal and false if they are not equal
   */
  @Override
  public boolean equals(Object o) {
    if(!(this.getClass() == o.getClass()))
      return false;
    User user = (User)o;
    return(!(this.userName != user.userName || this.personID != user.personID ||
            this.gender != user.gender || this.email != user.email ||
            this.firstName != user.firstName || this.lastName != user.lastName ||
            this.password != user.password));
  }

  /**
   * generates a unique 8 digit PersonID
   * @return 8 char long string that is unique
   */
  private String generatePersonID()
  {
    return UUID.randomUUID().toString();
  }
}
