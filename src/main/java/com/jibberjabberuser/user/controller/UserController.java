package com.jibberjabberuser.user.controller;

import com.jibberjabberuser.user.factory.UserFactory;
import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.model.dto.UserDTO;
import com.jibberjabberuser.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {
  
  private final UserService service;
  private final UserFactory factory = new UserFactory();
  
  public UserController(UserService service) {
    this.service = service;
  }
  
  @GetMapping(path = "/{id}")
  public UserDTO getUser(@PathVariable Long id) {
    return toDto(service.get(id));
  }
  
  @GetMapping(path = "/all")
  public List<UserDTO> getAllUsers() {
    return service.getAll().stream().map(this::toDto).collect(Collectors.toList());
  }
  
  @PostMapping()
  public void postUser(@RequestBody User user) {
    service.save(user);
  }
  
  @PostMapping(path = "/edit/{id}")
  public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO user) {
    final User oldUser = service.get(id);
    factory.update(oldUser, user);
    return toDto(service.save(oldUser));
  }
  
  private UserDTO toDto(User user) {
    return factory.userToDto(user);
  }
}
