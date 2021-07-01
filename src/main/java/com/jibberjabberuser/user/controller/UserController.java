package com.jibberjabberuser.user.controller;

import com.jibberjabberuser.user.factory.UserFactory;
import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.model.dto.ChangePasswordDTO;
import com.jibberjabberuser.user.model.dto.LogInDTO;
import com.jibberjabberuser.user.model.dto.UserDTO;
import com.jibberjabberuser.user.security.JwtTokenProvider;
import com.jibberjabberuser.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {
  
  
  private final AuthenticationManager authenticationManager;
  private final UserService service;
  private final UserFactory factory = new UserFactory();
  private final JwtTokenProvider tokenProvider;
  
  public UserController(UserService service, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
    this.service = service;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }
  
  @GetMapping(path = "/{id}")
  public UserDTO getUser(@PathVariable Long id) {
    return toDto(service.get(id));
  }
  
  @GetMapping()
  public UserDTO getUser() {
    return toDto(getAuthenticatedUser());
  }
  
  @GetMapping("/id")
  public Long getUserId() {
    return getAuthenticatedUser().getId();
  }
  
  @GetMapping(path = "/all")
  public List<UserDTO> getAllUsers() {
    return service.getAll().stream().map(this::toDto).collect(Collectors.toList());
  }
  
  @GetMapping(path = "/following")
  public List<Long> getFollowing() {
    return service.getFollowing(getUserId());
  }
  
  @PostMapping()
  public void createUser(@RequestBody User user) {
    service.save(user);
  }
  
  @PostMapping(path = "/edit")
  public UserDTO editUser(@RequestBody UserDTO user) {
    final User oldUser = getAuthenticatedUser();
    factory.update(oldUser, user);
    return toDto(service.save(oldUser));
  }
  
  @PostMapping(path = "/login")
  public String login(@RequestBody LogInDTO cred) {
    final String email = cred.getEmail();
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, cred.getPassword()));
    } catch (Exception e) {
      return e.getMessage();
    }
    return tokenProvider.createToken(email);
  }
  
  @PostMapping(path = "/changePassword")
  public void changePassword(@RequestBody ChangePasswordDTO dto) {
    final User user = getAuthenticatedUser();
    final boolean success = service.changPassword(user, dto);
    if (!success) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current password is incorrect");
  }
  
  @PostMapping(path = "/getUserByUserName")
  public UserDTO getUserByUserName(@RequestBody String name) {
    return toDto(service.getByUserName(name));
  }
  
  
  @PostMapping(path = "/like/{postId}")
  public void like(@PathVariable Long postId) {
    service.like(getUserId(), postId);
  }
  
  @PostMapping(path = "/unlike/{postId}")
  public void ubLike(@PathVariable Long postId) {
    service.unLike(getUserId(), postId);
  }
  //todo cambiar user id al @
  @PostMapping(path = "/follow/{userId}")
  public void follow(@PathVariable Long userId) {
    service.follow(getUserId(), userId);
  }
  
  @PostMapping(path = "/unfollow/{userId}")
  public void unFollow(@PathVariable Long userId) {
    service.unFollow(getUserId(), userId);
  }
  
  private UserDTO toDto(User user) {
    return factory.userToDto(user);
  }
  
  private User getAuthenticatedUser() {
    final org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final String email = principal.getUsername();
    return service.get(email);
  }
}
