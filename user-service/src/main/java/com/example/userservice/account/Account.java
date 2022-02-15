package com.example.userservice.account;

import com.example.userservice.entity.Entity;
import com.example.userservice.user.User;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "account")
public class Account extends Entity {

    @OneToOne
    @JoinColumn(name = "uid", unique = true)
    private User user;

    @Column(name = "username", unique = true)
    private String username = new String();

    @Column(name = "password")
    private String password = new String();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account() {
    }


}
