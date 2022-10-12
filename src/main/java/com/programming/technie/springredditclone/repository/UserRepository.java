package com.programming.technie.springredditclone.repository;

import com.programming.technie.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);


}
