package com.example.userservice.user;

import com.example.userservice.account.Account;
import com.example.userservice.model.NamedEntity;
import com.example.userservice.token.Token;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "user")
public class User extends NamedEntity implements Serializable {

    @Column(name = "mail")
    private String mail = new String();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Token> tokenList = new ArrayList<>();

    public User(User user) {
        this.account = user.getAccount();
        this.mail = user.getMail();

    }

    public User() {

    }

    public User(Integer id, String name, String mail) {
        super(id, name);
        this.mail = mail;
    }

    public List<Token> getToken() {
        return tokenList;
    }

    public void setToken(List<Token> token) {
        this.tokenList = token;
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
