package com.example.userservice.user;

import com.example.userservice.account.Account;
import com.example.userservice.model.NamedEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "user")
public class User extends NamedEntity {

    @Column(name = "mail")
    private String mail = new String();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    @Column(name = "token")
    private String token = new String();

    public User() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
