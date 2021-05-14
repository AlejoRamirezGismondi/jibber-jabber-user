package com.jibberjabberuser.user.service;


import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;
  
  
  public User save(User user) {
    return userRepository.save(user);
  }
}
