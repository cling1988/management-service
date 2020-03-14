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

import java.util.HashSet;
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
    public void initData() {
        Permission permission1 = new Permission();
        permission1.setName(CommonKey.PERMISSION_USER);
        permissionRepository.save(permission1);

        Permission permission2 = new Permission();
        permission2.setName(CommonKey.PERMISSION_PRODUCT);
        permissionRepository.save(permission2);

        Permission permission3 = new Permission();
        permission3.setName(CommonKey.PERMISSION_OUTLET);
        permissionRepository.save(permission3);

        Permission permission4 = new Permission();
        permission4.setName(CommonKey.PERMISSION_PRODUCTION);
        permissionRepository.save(permission4);

        Permission permission5 = new Permission();
        permission5.setName(CommonKey.PERMISSION_EVENT);
        permissionRepository.save(permission5);

        Permission permission6 = new Permission();
        permission6.setName(CommonKey.PERMISSION_PLANNING);
        permissionRepository.save(permission6);

        Permission permission7 = new Permission();
        permission7.setName(CommonKey.PERMISSION_REPORT);
        permissionRepository.save(permission7);

        Set<Permission> ps = new HashSet<>();
        ps.add(permissionRepository.save(permission1));
        ps.add(permissionRepository.save(permission2));
        ps.add(permissionRepository.save(permission3));
        ps.add(permissionRepository.save(permission4));
        ps.add(permissionRepository.save(permission5));
        ps.add(permissionRepository.save(permission6));
        ps.add(permissionRepository.save(permission7));


        Set<Permission> sap = new HashSet<>();
        sap.add(permissionRepository.save(permission1));
        Role role = new Role();
        role.setName(RoleName.SYSTEM_ADMIN.getValue());
        role.setPermissions(sap);
        roleRepository.save(role);


        Role role2 = new Role();
        role2.setName(RoleName.MANAGER.getValue());
        role2.setPermissions(ps);
        roleRepository.save(role2);

        Set<Role> r1 = new HashSet<Role>();
        r1.add(role);
        LoginUser u = new LoginUser();
        u.setActive(true);
        u.setType(ApplicationType.WEB);
        u.setUsername("admin");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u.setRoles(r1);
        loginUserRepository.save(u);

        log.info("user id :"+u.getId());

        UserProfile profile = new UserProfile();
        profile.setTitle("System Admin");
        profile.setName("System Admin");
        profile.setLoginUser(u);
        userProfileRepository.save(profile);

        log.info("Profile id :"+profile.getId());


    }

}
