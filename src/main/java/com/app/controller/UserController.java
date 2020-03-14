package com.app.controller;

import com.app.common.CommonKey;
import com.app.model.ChangePasswordRequest;
import com.app.model.ProfileListResponse;
import com.app.model.UserModel;
import com.app.service.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= CommonKey.URL_USER)
public class UserController {

    @Autowired
    private ManageUserService manageUserService;


    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserModel model) {
        return manageUserService.createNewUser(model);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<ProfileListResponse> getUserProfiles() {
        return manageUserService.getProfiles();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserModel> getUserProfile(@PathVariable Long id){
        return manageUserService.getUserProfileById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return manageUserService.deleteUser(id);
    }

    @PostMapping("/{id}/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest model){
        return manageUserService.changePassword(model);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProfile(@RequestBody UserModel model, @PathVariable Long id) {
        return manageUserService.updateProfile(model,id);
    }

}
