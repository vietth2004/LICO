package com.example.userservice.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TokenRepositories extends JpaRepository<Token, Integer>, JpaSpecificationExecutor<Token> {
}
