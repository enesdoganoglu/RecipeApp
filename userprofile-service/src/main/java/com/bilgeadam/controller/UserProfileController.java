package com.bilgeadam.controller;


import com.bilgeadam.service.UserProfileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;
import static com.bilgeadam.constant.ApiUrls.ACTIVATE_STATUS;

@RestController
@RequestMapping(USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;


    @GetMapping(ACTIVATE_STATUS + "/{userId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.activateStatus(userId));
    }


}