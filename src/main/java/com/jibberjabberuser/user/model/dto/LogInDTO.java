package com.jibberjabberuser.user.model.dto;

import lombok.Data;

@Data
public class LogInDTO {
  private final String email, password;
}
