package com.jibberjabberuser.user.model.dto;

import lombok.Data;

@Data
public class UserDTO {
  private String firstName, lastName, userName, email;
  private int age;
  private Long id;
  private boolean following;
  
  public UserDTO() {}
  public UserDTO(Long id, String firstName, String lastName, String userName, String email, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
    this.userName = userName;
    this.following = false;
  }
}
