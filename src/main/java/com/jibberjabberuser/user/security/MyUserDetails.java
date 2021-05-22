package com.jibberjabberuser.user.security;

import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetails implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  public MyUserDetails(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Optional<User> optional = userRepository.findByEmail(username);
    
    if (!optional.isPresent()) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    final User user = optional.get();
    return org.springframework.security.core.userdetails.User//
            .withUsername(username)//
            .password(user.getPassword())//
//            .authorities(user.getRoles())//
            .authorities("normal")//
            .accountExpired(false)//
            .accountLocked(false)//
            .credentialsExpired(false)//
            .disabled(false)//
            .build();
  }
  
}
