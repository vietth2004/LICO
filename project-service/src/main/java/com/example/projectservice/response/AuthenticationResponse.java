package com.example.projectservice.response;

public class AuthenticationResponse {

    private final String jwt;
    private Integer id;
    private Boolean success;

    public AuthenticationResponse(String jwt, Integer id) {
        this.jwt = jwt;
        this.id = id;
        this.success = true;
    }

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
        this.success = false;
    }

    public String getJwt() {
        return jwt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
