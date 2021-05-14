package com.jibberjabberuser.user.repository;


import com.jibberjabberuser.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
