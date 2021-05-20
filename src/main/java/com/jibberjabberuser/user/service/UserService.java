package com.jibberjabberuser.user.service;


import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
  
  public User get(Long id) {
    final Optional<User> optional = userRepository.findById(id);
    if (optional.isPresent()) return optional.get();
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
  }
  
  public List<User> getAll() {
    return userRepository.findAll();
  }
}
