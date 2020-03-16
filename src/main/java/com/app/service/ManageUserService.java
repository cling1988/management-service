package com.app.service;

import com.app.model.ChangePasswordRequest;
import com.app.model.ProfileListResponse;
import com.app.model.RoleModel;
import com.app.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface ManageUserService {

    public ResponseEntity<?> createNewUser(UserModel model);

    public ResponseEntity<ProfileListResponse> getProfiles();

    public ResponseEntity<UserModel> getUserProfileById(Long id);

    public ResponseEntity<?> updateProfile(UserModel model, Long id);

    public ResponseEntity<?> changePassword(ChangePasswordRequest model);

    public ResponseEntity<?> deleteUser(Long id);

    public ResponseEntity<?> createNewRole(RoleModel model);

    public ResponseEntity<?> getRoles();

    public ResponseEntity<RoleModel> getRoleById(Long id);

    public ResponseEntity<?> updateRole(RoleModel model, Long id);

    public ResponseEntity<?> deleteRole(Long id);

    public ResponseEntity<?> getPermission();

    public ResponseEntity<?> assignRole(RoleModel role, Long userId);

}
