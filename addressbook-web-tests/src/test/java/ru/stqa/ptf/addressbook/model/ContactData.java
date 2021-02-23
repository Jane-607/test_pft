package ru.stqa.ptf.addressbook.model;

import java.util.Objects;

public class ContactData {
  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String company;
  private final String home;
  private final String email;
  private final String group;

  public ContactData(String firstName, String middleName, String lastName, String company, String home, String email, String  group) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.company = company;
    this.home = home;
    this.email = email;
    this.group = group;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", home='" + home + '\'' +
            ", email='" + email + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(home, that.home) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, home, email);
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

  public String getGroup() { return group; }
}

