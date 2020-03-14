package com.app.model;

import java.io.Serializable;
import java.util.List;

public class TokenResponse implements Serializable {

    private String username;

    private String jwtToken;

    private String currentDatetime;

    private List<String> permissions;

    public TokenResponse(String username, String jwtToken) {
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getCurrentDatetime() {
        return currentDatetime;
    }

    public void setCurrentDatetime(String currentDatetime) {
        this.currentDatetime = currentDatetime;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
