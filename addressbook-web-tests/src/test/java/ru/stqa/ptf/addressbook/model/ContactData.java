package ru.stqa.ptf.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String company;
  private final String home;
  private final String email;

  public void setId(int id) {
    this.id = id;
  }

  private final String group;

  public ContactData(String firstName, String middleName, String lastName, String company, String home, String email, String group) {
    this.id = 0;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.company = company;
    this.home = home;
    this.email = email;
    this.group = group;
  }

  public ContactData(int id, String firstName, String middleName, String lastName, String company, String home, String email, String group) {
    this.id = Integer.MAX_VALUE;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.company = company;
    this.home = home;
    this.email = email;
    this.group = group;
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

  public int getId() { return id; }


  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstName, that.firstName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName);
  }
}

