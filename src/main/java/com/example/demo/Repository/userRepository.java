package com.example.demo.Repository;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String string);
}
