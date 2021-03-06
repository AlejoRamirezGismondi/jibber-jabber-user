package com.jibberjabberuser.user.factory;

import com.jibberjabberuser.user.model.User;
import com.jibberjabberuser.user.model.dto.UserDTO;

public class UserFactory {
  
  public void update(User o, UserDTO n) {
    if (n.getAge() != 0) o.setAge(n.getAge());
    if (n.getFirstName() != null) o.setFirstName(n.getFirstName());
    if (n.getLastName() != null) o.setLastName(n.getLastName());
    if (n.getEmail() != null) o.setEmail(n.getEmail());
  }
  
  public User dtoToUser(UserDTO dto) {
    return new User(
            dto.getFirstName(),
            dto.getLastName(),
            dto.getUserName(),
            dto.getEmail(),
            dto.getAge()
    );
  }
  
  public UserDTO userToDto(User user) {
    return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getUserName(),
            user.getEmail(),
            user.getAge());
  }
}
