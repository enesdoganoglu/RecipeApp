package com.bilgeadam.controller;


import com.bilgeadam.service.UserProfileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequestMapping(USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;


    @GetMapping(ACTIVATE_STATUS + "/{userId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.activateStatus(userId));
    }

    @DeleteMapping(DELETE_BY_ID + "/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.delete(userId));
    }


}