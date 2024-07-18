package com.example.demo.token;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    Optional<Token>  findByToken(String token);

    List<Token> findByUserId(Integer id);

   void deleteTokensByUserId(Integer id);


}
