package com.jibberjabberuser.user.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "userName"})
})
@Data
public class User {
  @Id @GeneratedValue
  private Long id;
  @NotNull private String firstName, lastName, password;
  @Column(name = "userName")
  @NotNull private String userName;
  @Column(name = "email")
  @NotNull private String email;
  private int age;
  @ElementCollection @CollectionTable(name = "likes")
  private List<Long> likeIds = new ArrayList<>();
  @ManyToMany
  private List<User> following = new ArrayList<>();
  
  public User(String firstName, String lastName, String userName, String email, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
    this.userName = userName;
  }
  public User() {}
  
  public void addLike(Long postId) {
    likeIds.add(postId);
  }
  
  public void removeLike(Long postId) {
    likeIds.remove(postId);
  }
  
  public void follow(User user) {
    following.add(user);
  }
  
  public void unfollow(User user) {
    following.remove(user);
  }
}
