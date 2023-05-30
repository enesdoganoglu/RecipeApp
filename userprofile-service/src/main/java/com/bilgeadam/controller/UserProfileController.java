package com.bilgeadam.controller;


import com.bilgeadam.dto.request.PasswordChangeDto;
import com.bilgeadam.dto.request.RegisterAddressDto;
import com.bilgeadam.dto.request.UserProfileUpdateRequestDto;
import com.bilgeadam.dto.response.UserProfileChangePasswordResponseDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;
import static com.bilgeadam.constant.ApiUrls.FORGOT_PASSWORD;


@RestController
@RequestMapping(USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;


    @PutMapping(PASS_CHANGE)
    public ResponseEntity<Boolean> passwordChange(PasswordChangeDto dto){
        return ResponseEntity.ok(userProfileService.passwordChange(dto));
    }

    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserProfileChangePasswordResponseDto dto){
        return ResponseEntity.ok(userProfileService.forgotPassword(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
    @GetMapping(ACTIVATE_STATUS + "/{userId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.activateStatus(userId));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<UserProfile> update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @PostMapping(ADDRESS_REGISTER) //register2
    public ResponseEntity<UserProfile> createAddressWithRabbitMq(@RequestBody @Valid RegisterAddressDto dto){
        return ResponseEntity.ok(userProfileService.createAddressWithRabbitMq(dto));
    }

    @DeleteMapping(DELETE_BY_ID + "/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.delete(userId));
    }

    @GetMapping("/find-by-username/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }
    @GetMapping("/find-by-role/{role}")
    public ResponseEntity<List<UserProfile>> findByRole(@PathVariable String role){
        return ResponseEntity.ok(userProfileService.findByRole(role));
    }


    @GetMapping("/find-by-userprofile-dto/{userId}")
    public ResponseEntity<UserProfileResponseDto> findByUserProfileDto(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.findByUserProfileDto(userId));
    }



}