package com.jibberjabberuser.user.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class User {
  @Id @GeneratedValue
  private Long id;
  private String firstName, lastName;
  private int age;
}
