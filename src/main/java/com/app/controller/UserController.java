package com.app.controller;

import com.app.common.CommonKey;
import com.app.model.ChangePasswordRequest;
import com.app.model.ProfileListResponse;
import com.app.model.RoleModel;
import com.app.model.UserModel;
import com.app.service.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private ManageUserService manageUserService;

    @PostMapping(CommonKey.URL_USER_EDIT)
    public ResponseEntity<?> createUser(@RequestBody UserModel model) {
        return manageUserService.createNewUser(model);
    }

    @GetMapping(CommonKey.URL_USER_VIEW)
    public ResponseEntity<ProfileListResponse> getUserProfiles() {
        return manageUserService.getProfiles();
    }

    @GetMapping(CommonKey.URL_USER_VIEW+"/{id}")
    public ResponseEntity<UserModel> getUserProfile(@PathVariable Long id){
        return manageUserService.getUserProfileById(id);
    }

    @DeleteMapping(CommonKey.URL_USER_EDIT+"/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return manageUserService.deleteUser(id);
    }

    @PostMapping(CommonKey.URL_USER_EDIT+"/{id}/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest model){
        return manageUserService.changePassword(model);
    }

    @PostMapping(CommonKey.URL_USER_EDIT+"/{id}/roles")
    public ResponseEntity<?> assignRole(@RequestBody RoleModel model, @PathVariable Long id){
        return manageUserService.assignRole(model, id);
    }

    @PutMapping(CommonKey.URL_USER_EDIT+"/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody UserModel model, @PathVariable Long id) {
        return manageUserService.updateProfile(model,id);
    }

    @PostMapping(CommonKey.URL_USER_EDIT+"/roles")
    public ResponseEntity<?> createRole(@RequestBody RoleModel model){
        return manageUserService.createNewRole(model);
    }

    @GetMapping(CommonKey.URL_USER_VIEW+"/roles")
    public ResponseEntity<?> getRoles(){
        return manageUserService.getRoles();
    }

    @GetMapping(CommonKey.URL_USER_VIEW+"/roles/{id}")
    public ResponseEntity<?> getRolesById(@PathVariable Long id){
        return manageUserService.getRoleById(id);
    }

    @PutMapping(CommonKey.URL_USER_EDIT+"/roles/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleModel model, @PathVariable Long id){
        return manageUserService.updateRole(model,id);
    }

    @DeleteMapping(CommonKey.URL_USER_EDIT+"/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
        return manageUserService.deleteRole(id);
    }

    @GetMapping(CommonKey.URL_USER_VIEW+"/permissions")
    public ResponseEntity<?> getPermissions(){
        return manageUserService.getPermission();
    }
}
