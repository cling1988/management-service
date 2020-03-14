package com.app.service.impl;

import com.app.common.ApplicationType;
import com.app.common.LocalDatetimeUtil;
import com.app.common.exception.PasswordNotMatchException;
import com.app.common.exception.ProductNotFoundException;
import com.app.common.exception.UserNotFoundException;
import com.app.entity.LoginUser;
import com.app.entity.Product;
import com.app.entity.Role;
import com.app.entity.UserProfile;
import com.app.model.ChangePasswordRequest;
import com.app.model.ProfileListResponse;
import com.app.model.UserModel;
import com.app.repository.LoginUserRepository;
import com.app.repository.RoleRepository;
import com.app.repository.UserProfileRepository;
import com.app.service.ManageUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Override
    public ResponseEntity<?> createNewUser(UserModel model) {

        LoginUser loginUser = createLoginUser(model.getUsername(), model.getPassword(), ApplicationType.WEB);

        UserProfile profile = new UserProfile();
        profile.setName(model.getName());
        profile.setEmployeeId(model.getEmployeeId());
        profile.setTitle(model.getTitle());
        profile.setContact(model.getContact());
        profile.setEmail(model.getEmail());
        profile.setBirthday(model.getBirthday().toString());
        profile.setJoinDate(model.getJoinDate().toString());
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
        return null;
    }

    @Override
    public ResponseEntity<?> updateProfile(UserModel model, Long id) {
        UserProfile p = mapToUserProfile(model);
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
        model.setJoinDate(LocalDatetimeUtil.parseDate(profile.getJoinDate()));
        model.setUser_id(profile.getLoginUser().getId());
        model.setUsername(profile.getLoginUser().getUsername());
        model.setType(profile.getLoginUser().getType());
        model.setActive(profile.getLoginUser().isActive());
        return model;
    }

    private UserProfile mapToUserProfile(UserModel model) {
        if (null == model) {
            return null;
        }
        UserProfile profile = new UserProfile();
        profile.setId(model.getId());
        profile.setName(model.getName());
        profile.setEmployeeId(model.getEmployeeId());
        profile.setTitle(model.getTitle());
        profile.setEmail(model.getEmail());
        profile.setContact(model.getContact());
        profile.setBirthday(model.getBirthday().toString());
        profile.setJoinDate(model.getJoinDate().toString());

        return profile;
    }

    private LoginUser createLoginUser(String username, String password, ApplicationType type) {
        Optional<Role> role = roleRepository.findByName("MANAGER");
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
