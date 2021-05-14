package com.jibberjabberuser.user.controller;

import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @PostMapping()
  public void postUser(@RequestBody User user) {
    userService.save(user);
  }
}
