package com.bilgeadam.manager;


import com.bilgeadam.dto.request.UserProfileChangePasswordRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.DELETE_BY_ID;
import static com.bilgeadam.constant.ApiUrls.FORGOT_PASSWORD;


@FeignClient(url = "http://localhost:8080/api/v1/user-profile", name = "user-userprofile")
public interface IUserProfileManager {


    @GetMapping("/activate-status/{userId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long userId);

    @DeleteMapping(DELETE_BY_ID + "/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long userId);

    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserProfileChangePasswordRequestDto dto);


}
