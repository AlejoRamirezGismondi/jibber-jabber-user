package com.jibberjabberuser.user.model.dto;

import lombok.Data;

@Data
public class UserDTO {
  private String firstName, lastName, email;
  private int age;
  
  public UserDTO() {}
  public UserDTO(String firstName, String lastName, String email, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }
}