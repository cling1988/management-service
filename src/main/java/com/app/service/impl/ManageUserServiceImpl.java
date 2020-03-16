package com.app.service.impl;

import com.app.common.ApplicationType;
import com.app.common.LocalDatetimeUtil;
import com.app.common.exception.PasswordNotMatchException;
import com.app.common.exception.ProductNotFoundException;
import com.app.common.exception.UserNotFoundException;
import com.app.entity.*;
import com.app.model.*;
import com.app.repository.LoginUserRepository;
import com.app.repository.PermissionRepository;
import com.app.repository.RoleRepository;
import com.app.repository.UserProfileRepository;
import com.app.service.ManageUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManageUserServiceImpl implements ManageUserService {
    private static final Logger log = LogManager.getLogger(ManageUserServiceImpl.class);

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ResponseEntity<?> createNewUser(UserModel model) {

        LoginUser loginUser = createLoginUser(model.getUsername(), model.getPassword(), ApplicationType.WEB,model.getRoleId());

        UserProfile profile = new UserProfile();
        profile.setName(model.getName());
        profile.setEmployeeId(model.getEmployeeId());
        profile.setTitle(model.getTitle());
        profile.setContact(model.getContact());
        profile.setEmail(model.getEmail());
        profile.setBirthday(model.getBirthday().toString());
        profile.setLoginUser(loginUser);

        UserProfile newProfile = userProfileRepository.save(profile);

        return ResponseEntity.ok(newProfile.getId());

    }

    @Override
    public ResponseEntity<ProfileListResponse> getProfiles() {
        ProfileListResponse profileListResponse = new ProfileListResponse();

        List<UserProfile> profiles = userProfileRepository.findAll();
        profileListResponse.setResults(profiles.stream().map(p -> mapToUserModel(p)).collect(Collectors.toList()));

        return ResponseEntity.ok(profileListResponse);
    }

    @Override
    public ResponseEntity<UserModel> getUserProfileById(Long id) {
        Optional<UserProfile> profile = userProfileRepository.findById(id);

        if (profile.isPresent()) {
            UserModel model = mapToUserModel(profile.get());

            return ResponseEntity.ok(model);
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public ResponseEntity<?> updateProfile(UserModel model, Long id) {
        Optional<UserProfile> opt = userProfileRepository.findById(id);
        opt.orElseThrow(()->new UserNotFoundException(id));
        UserProfile profile = opt.get();
        UserProfile p = mapToUserProfile(profile, model);
        p.setId(id);
        userProfileRepository.save(p);

        return ResponseEntity.ok(p.getId());
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest model) {
        Optional<LoginUser> opt = loginUserRepository.findById(model.getId());
        if (opt.isPresent()) {
            LoginUser lu = opt.get();
            String cp = bCryptPasswordEncoder.encode(model.getPassword());
            if (!lu.getPassword().equals(cp)) {
                throw new PasswordNotMatchException();
            }
            lu.setPassword(bCryptPasswordEncoder.encode(model.getNewPassword()));
            loginUserRepository.save(lu);

            return ResponseEntity.noContent().build();
        }

        throw new UserNotFoundException(model.getId());

    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        Optional<UserProfile> p = userProfileRepository.findById(id);
        if (p.isPresent()) {
            UserProfile pp = p.get();
            pp.setDeleted(true);
            pp.getLoginUser().setDeleted(true);
//            LoginUser user = pp.getLoginUser();
//            user.setDeleted(true);
//            loginUserRepository.save(user);
            userProfileRepository.save(pp);

            return ResponseEntity.noContent().build();

        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public ResponseEntity<?> createNewRole(RoleModel model) {

//        List<> permissionRepository.findAll();
        Set<Permission> permissions = new HashSet<>();

//        for(PermissionModel pm : model.getPermissions()){
//            permissions.add(new Permission(pm.getId(),pm.getName()));
//        }
        permissions.addAll(model.getPermissions().stream().map(
                m->new Permission(m.getId(),m.getName())).collect(Collectors.toList()));


        Role role = new Role();
        role.setName(model.getName());
        role.setPermissions(permissions);

        roleRepository.save(role);

        return ResponseEntity.ok(role.getId());
    }

    @Override
    public ResponseEntity<?> getRoles() {

        List<Role> roles = roleRepository.findByEditable(true);
        RoleListResponse res  = new RoleListResponse();
        res.setResults(roles.stream().map(r -> new RoleModel(r.getId(),r.getName())).collect(Collectors.toList()));
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<RoleModel> getRoleById(Long id) {
        Optional<Role> opt = roleRepository.findById(id);
        if(opt.isPresent()){
            Role role = opt.get();

            RoleModel rm = new RoleModel(role.getId(),role.getName());
            rm.setPermissions(role.getPermissions().stream().map(p-> new PermissionModel(p.getId(),p.getName())).collect(Collectors.toList()));
            return ResponseEntity.ok(rm);
        }

        throw new UserNotFoundException(id);
    }

    @Override
    public ResponseEntity<?> updateRole(RoleModel model, Long id) {
        Optional<Role> opt = roleRepository.findById(id);
        opt.orElseThrow(()->new UserNotFoundException(id));
        Role role = opt.get();
        role.setName(model.getName());

        Set<Permission> ps = new HashSet<>();
        ps.addAll(model.getPermissions().stream().map(p->new Permission(p.getId(),p.getName())).collect(Collectors.toList()));
        role.setPermissions(ps);

        roleRepository.save(role);
        return ResponseEntity.ok(role.getId());
    }

    @Override
    public ResponseEntity<?> deleteRole(Long id) {
        Optional<Role> opt = roleRepository.findById(id);
        opt.orElseThrow(()->new UserNotFoundException(id));
        Role r = opt.get();
        r.setDeleted(true);
        roleRepository.save(r);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> getPermission() {
        List<Permission> permissions=permissionRepository.findAll();
        PermissionListResponse response = new PermissionListResponse();
        response.setResults(permissions.stream().map(p->new PermissionModel(p.getId(),p.getName())).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> assignRole(RoleModel role, Long userId) {
        Optional<LoginUser> opt1= loginUserRepository.findById(userId);
        opt1.orElseThrow(()->new UserNotFoundException(userId));

        Optional<Role> opt2 = roleRepository.findById(role.getId());
        opt2.orElseThrow(()->new UserNotFoundException(role.getId()));

        Set<Role> roles = new HashSet<>();
        roles.add(opt2.get());
        LoginUser user = opt1.get();
        user.setRoles(roles);

        loginUserRepository.save(user);

        return ResponseEntity.ok(user.getId());
    }

    private UserModel mapToUserModel(UserProfile profile) {
        if (null == profile) {
            return null;
        }
        UserModel model = new UserModel();
        model.setId(profile.getId());
        model.setName(profile.getName());
        model.setEmployeeId(profile.getEmployeeId());
        model.setTitle(profile.getTitle());
        model.setContact(profile.getContact());
        model.setEmail(profile.getEmail());
        model.setBirthday(LocalDatetimeUtil.parseDate(profile.getBirthday()));
        model.setUser_id(profile.getLoginUser().getId());
        model.setUsername(profile.getLoginUser().getUsername());
        model.setType(profile.getLoginUser().getType());
        model.setActive(profile.getLoginUser().isActive());

        if(profile.getLoginUser().getRoles()!=null){
            Role role = profile.getLoginUser().getRoles().iterator().next();
            model.setRoleId(role.getId());
            model.setRoleName(role.getName());
            if(role.getPermissions()!=null){
                model.setPermissions(role.getPermissions().stream().map(
                        p->new PermissionModel(p.getId(),p.getName())).collect(Collectors.toList()));
            }
        }

        return model;
    }

    private UserProfile mapToUserProfile(UserProfile profile, UserModel model) {
        if (null == model) {
            return null;
        }
        profile.setId(model.getId());
        profile.setName(model.getName());
        profile.setEmployeeId(model.getEmployeeId());
        profile.setTitle(model.getTitle());
        profile.setEmail(model.getEmail());
        profile.setContact(model.getContact());
        profile.setBirthday(model.getBirthday().toString());

        return profile;
    }

    private LoginUser createLoginUser(String username, String password, ApplicationType type,Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());

        LoginUser u = new LoginUser();
        u.setActive(true);
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setType(type);
        u.setRoles(roles);
        return loginUserRepository.save(u);
    }
}
