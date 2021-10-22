package com.challenge.warmup.dto;

import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.NotBlank;

public class NewUser {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private Set<String> roles = new HashSet<>();

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
