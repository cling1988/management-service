package com.app.entity;

import javax.persistence.*;

@Entity
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="loginuser_id_seq")
    @SequenceGenerator(name="loginuser_id_seq", sequenceName="loginuser_id_seq", allocationSize=1)
    private long id;
    private String username;
    private String password;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
