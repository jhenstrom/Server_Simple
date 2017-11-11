package model_classes;

import java.util.UUID;

/** Stores information about a person*/
public class Person
{
  /** Person's first name*/
  private String firstName;
  /** Person's last name*/
  private String lastName;
  /** Person's gender*/
  private String gender;
  /** Unique idenifier for this person*/
  private String personID;
  /** personID of person's father*/
  private String father;
  /** personID of person's mother*/
  private String mother;
  /** personID of person's spouce*/
  private String spouse;
  /** User to whom this person belongs*/
  private String descendant;

  /**
  * Contrutor to create Person object
  * @param firstName Person's first name
  * @param lastName Person's last name
  * @param gender Person's gender
  * @param descendant User to whom this person belongs
  * @param father personID of person's father
  * @param mother personID of person's mother
  * @param spouce personID of person's spouce
  */
  public Person(String firstName, String lastName, String gender, String descendant, String father, String mother, String spouce)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.descendant = descendant;
    this.father = father;
    this.mother = mother;
    this.spouse = spouce;
    this.personID = generatePersonID();

  }

  /**
   * Contrutor to create Person object
   * @param user USer used to pull relevant data to construct person
   */
  public Person(User user)
  {
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.gender = user.getGender();
    this.descendant = user.getUserName();
    this.father = null;
    this.mother = null;
    this.spouse = null;
    this.personID = user.getPersonID();
  }

  /**
   * Contrutor to create Person object
   * @param personID personID used for this person
   * @param firstName Person's first name
   * @param lastName Person's last name
   * @param gender Person's gender
   * @param descendant User to whom this person belongs
   * @param father personID of person's father
   * @param mother personID of person's mother
   * @param spouce personID of person's spouce
   */
  public Person(String personID, String firstName, String lastName, String gender, String descendant, String father, String mother, String spouce)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.descendant = descendant;
    this.father = father;
    this.mother = mother;
    this.spouse = spouce;
    this.personID = personID;

  }


  public String getFirstName(){return firstName; }

  public String getLastName(){ return lastName; }

  public String getGender(){ return gender; }

  public String getPersonID(){return personID; }

  public String getDescendant(){ return descendant; }

  public String getFather(){ return father; }

  public String getMother(){ return mother; }

  public String getSpouce(){ return spouse; }


  public void setFather(String fatherID){ this.father = fatherID; }

  public void setMother(String motherID){ this.mother = motherID; }

  public void setSpouce(String spouceID){this.spouse = spouceID; }

  /**
  * Method to create string obj of Person
  * @return String of Person
  */
  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("Person ID: " + personID + "\n");
    s.append("Name: " + firstName + " " + lastName + "\n");
    s.append("Gender: " + gender + "\n");
    s.append("Decendent: " + descendant + "\n");
    if (father != null)
      s.append("Father: " + father + "\n");
    if (mother != null)
      s.append("Mother: " + father + "\n");
    if (spouse != null)
      s.append("Spouce: " + father + "\n");
    return s.toString();
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
