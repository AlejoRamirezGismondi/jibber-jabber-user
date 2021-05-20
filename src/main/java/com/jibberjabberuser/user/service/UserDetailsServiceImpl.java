package com.jibberjabberuser.user.service;

import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.model.UserDetailsImpl;
import com.jibberjabberuser.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final Optional<User> optional = userRepository.findByEmail(email);
    if (!optional.isPresent()) throw new UsernameNotFoundException("No user found with email: " + email);
    return new UserDetailsImpl(optional.get());
  }
}
