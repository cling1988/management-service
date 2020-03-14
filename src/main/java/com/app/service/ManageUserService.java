package com.app.service;

import com.app.model.ChangePasswordRequest;
import com.app.model.ProfileListResponse;
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

}
