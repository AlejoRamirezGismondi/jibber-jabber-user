package com.jibberjabberuser.user.service;


import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.model.dto.ChangePasswordDTO;
import com.jibberjabberuser.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  public User update(User user) {
    return userRepository.save(user);
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
  
  public User get(String email) {
    final Optional<User> optional = userRepository.findByEmail(email);
    if (optional.isPresent()) return optional.get();
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
  }
  
  public boolean changPassword(User user, ChangePasswordDTO dto) {
    final String oldPassword = user.getPassword();
    if (!passwordEncoder.matches(dto.getOldPassword(), oldPassword)) return false;
    user.setPassword(dto.getNewPassword());
    save(user);
    return true;
  }
  
  public User getByFirstName(String userName) {
    final Optional<User> optional = userRepository.findByFirstName(userName);
    if (optional.isPresent()) return optional.get();
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
  }
  
  public void like(Long userId, Long postId) {
    final User user = get(userId);
    user.addLike(postId);
    userRepository.save(user);
  }
  
  public void unLike(Long userId, Long postId) {
    final User user = get(userId);
    user.removeLike(postId);
    userRepository.save(user);
  }
  
  public void follow(Long id, Long followId) {
    if (id.equals(followId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't follow itself");
    final User user = get(id);
    final User follow = get(followId);
    user.follow(follow);
    userRepository.save(user);
  }
  
  public void unFollow(Long id, Long followId) {
    if (id.equals(followId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't unfollow itself");
    final User user = get(id);
    final User follow = get(followId);
    user.unfollow(follow);
    userRepository.save(user);
  }
  
  public List<Long> getFollowing(Long userId) {
    return get(userId).getFollowing().stream()
            .map(User::getId).collect(Collectors.toList());
  }
}
//todo estara mal que esten los errors aca en el service?
