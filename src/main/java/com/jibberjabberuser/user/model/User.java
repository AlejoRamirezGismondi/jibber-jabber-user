package com.jibberjabberuser.user.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class User {
  @Id @GeneratedValue
  private Long id;
  private String firstName, lastName;
  @NotNull private String password, email;
  private int age;
  
  public User(String firstName, String lastName, String email, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }
  public User() {}
}
