package com.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import java.util.List;

public class RoleModel {

    private long id;

    private String name;

    private List<PermissionModel> permissions;

    public RoleModel(){

    }

    public RoleModel(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionModel> permissions) {
        this.permissions = permissions;
    }
}
