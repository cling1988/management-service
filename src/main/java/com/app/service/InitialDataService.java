package com.app.service;

import com.app.entity.Permission;
import com.app.entity.Role;

import java.util.Set;

public interface InitialDataService {

    public boolean initData();

    public Permission createPermissionIfNotFound(String permission);

    public Role createRoleIfNotFound(String roleName, Set<Permission> permissions);

}
