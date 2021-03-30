package ru.stqa.ptf.mantis.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "mantis_user_table")
public class UserData {

  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;
  @Column(name = "username")
  private String name;
  @Column(name = "email")
  private String email;

  public int getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }


  public UserData withUser(String name) {
    this.name = name;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData = (UserData) o;
    return id == userData.id && Objects.equals(name, userData.name) && Objects.equals(email, userData.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email);
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
