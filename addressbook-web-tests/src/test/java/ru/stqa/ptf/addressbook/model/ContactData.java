package ru.stqa.ptf.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.File;
import java.util.Objects;


@XStreamAlias("contact")
public class ContactData {
  @XStreamOmitField
  private int id = Integer.MAX_VALUE;
  @Expose
  private String firstName;
  @Expose
  private String middleName;
  @Expose
  private String lastName;
  @Expose
  private String company;
  @Expose
  private String address;
  @Expose
  private String home;
  @Expose
  private String mobile;
  @Expose
  private String work;
  @Expose
  private String email;
  @Expose
  private String email2;
  @Expose
  private String email3;
  @Expose
  private String group;

  private File photo;

  private String allPhones;
  private String allEmails;


  public ContactData withId(int id) { this.id = id; return this; }
  public ContactData withFirstName(String firstName) { this.firstName = firstName; return this; }
  public ContactData withMiddleName(String middleName) { this.middleName = middleName; return this; }
  public ContactData withLastName(String lastName) { this.lastName = lastName; return this; }
  public ContactData withPhoto(File photo) { this.photo = photo; return this; }
  public ContactData withCompany(String company) { this.company = company; return this; }
  public ContactData withAddress(String address) { this.address = address; return this; }
  public ContactData withHome(String home) { this.home = home; return this; }
  public ContactData withMobile (String mobile) { this.mobile = mobile; return this; }
  public ContactData withWork (String work) { this.work = work; return this; }
  public ContactData withEmail(String email) { this.email = email; return this; }
  public ContactData withEmail2(String email2) { this.email2 = email2; return this; }
  public ContactData withEmail3(String email3) { this.email3 = email3; return this; }
  public ContactData withGroup(String group) { this.group = group; return this; }

  public ContactData withAllPhones(String allPhones) { this.allPhones = allPhones; return this;}
  public ContactData withAllEmails(String allEmails) { this.allEmails = allEmails; return this; }

  public int getId() {
    return id;
  }
  public String getFirstName() { return firstName; }
  public String getMiddleName() { return middleName; }
  public String getLastName() { return lastName; }
  public File getPhoto() { return photo; }
  public String getCompany() { return company; }
  public String getAddress() { return address; }
  public String getEmail() { return email; }
  public String getEmail2() { return email2; }
  public String getEmail3() { return email3; }
  public String getHome() { return home; }
  public String getMobile() { return mobile; }
  public String getWork() { return work; }
  public String getGroup() { return group; }


  public String getAllPhones() { return allPhones; }
  public String getAllEmails() { return allEmails; }


  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            '}';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id && Objects.equals(firstName, that.firstName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName);
  }

}

