package com.app.service.impl;

import com.app.common.ApplicationType;
import com.app.common.CommonKey;
import com.app.common.RoleName;
import com.app.entity.LoginUser;
import com.app.entity.Permission;
import com.app.entity.Role;
import com.app.entity.UserProfile;
import com.app.repository.LoginUserRepository;
import com.app.repository.PermissionRepository;
import com.app.repository.RoleRepository;
import com.app.repository.UserProfileRepository;
import com.app.service.InitialDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class InitialDataServiceImpl implements InitialDataService {
    private static final Logger log = LogManager.getLogger(InitialDataServiceImpl.class);

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public boolean initData() {
        Set<Permission> sap = new HashSet<>();

        log.info("Create permission");
        for (String name : CommonKey.getAllPermission()) {
            Permission p = createPermissionIfNotFound(name);
            if (name.equals(CommonKey.PERMISSION_USER_VIEW) || name.equals(CommonKey.PERMISSION_USER_EDIT)) {
                sap.add(p);
            }
        }
        log.info("DONE create permission");

        log.info("Create Role");
        Role role = createRoleIfNotFound(RoleName.SYSTEM_ADMIN.getValue(), sap);
        log.info("DONE create role");

        Optional<LoginUser> opt = loginUserRepository.findByUsernameAndActive("admin",true);
        if(opt.isPresent()){
            return true;
        }
        Set<Role> r1 = new HashSet<Role>();
        r1.add(role);
        LoginUser u = new LoginUser();
        u.setActive(true);
        u.setType(ApplicationType.WEB);
        u.setUsername("admin");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u.setRoles(r1);
        loginUserRepository.save(u);

        log.info("user id :" + u.getId());

        UserProfile profile = new UserProfile();
        profile.setTitle("System Admin");
        profile.setName("System Admin");
        profile.setLoginUser(u);
        userProfileRepository.save(profile);

        log.info("Profile id :" + profile.getId());

        return true;

    }

    @Override
    public Permission createPermissionIfNotFound(String name) {
        Optional<Permission> opt = permissionRepository.findByName(name);
        if (opt.isPresent()) {
            return opt.get();
        }
        Permission permission = new Permission();
        permission.setName(name);
        permissionRepository.save(permission);
        return permission;
    }

    @Override
    public Role createRoleIfNotFound(String roleName, Set<Permission> permissions) {
        Optional<Role> opt = roleRepository.findByName(roleName);
        if (opt.isPresent()) {
            return opt.get();
        }
        Role role = new Role();
        role.setName(roleName);
        role.setPermissions(permissions);
        role.setEditable(false);
        roleRepository.save(role);

        return role;
    }


}
