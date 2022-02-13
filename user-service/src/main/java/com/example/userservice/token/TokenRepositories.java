package com.example.userservice.token;

import com.example.userservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TokenRepositories extends JpaRepository<Token, Integer>, JpaSpecificationExecutor<Token> {
}
