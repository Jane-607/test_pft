package ru.stqa.ptf.addressbook.model;

public class ContactDate {
  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String company;
  private final String home;
  private final String email;

  public ContactDate(String firstName, String middleName, String lastName, String company, String home, String email) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.company = company;
    this.home = home;
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getCompany() { return company; }

  public String getHome() { return home; }

  public String getEmail() { return email; }
}
