package com.jwtwebsecurity.example.jwtsecurity.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    // @NotBlank
    // @Size(min=3, max = 50)
    private String name;

    // @NotBlank
    // @Size(min=3, max = 50)
    private String username;

    // @NotBlank
    // @Size(max = 50)
    // @Email
    private String email;

    // @NotBlank
    // @Size(min=6, max = 100)
    private String password;

    private boolean activated;

    // private Set<Role> roles = new HashSet<>();

    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String username, String email, String password, boolean activated) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}