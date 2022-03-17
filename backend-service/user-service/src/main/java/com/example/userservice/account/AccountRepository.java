package com.example.userservice.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    @Query("SELECT DISTINCT account from Account account WHERE account.username= :username")
    @Transactional
    Account findUserByUsername(String username);

    @Query("SELECT DISTINCT account from Account account WHERE account.id= :id")
    @Transactional
    Account findByID(Integer id);
}
