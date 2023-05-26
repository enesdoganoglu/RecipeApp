package com.bilgeadam.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@FeignClient(url = "http://localhost:8080/api/v1/user-profile", name = "user-userprofile")
public interface IUserProfileManager {


    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);


}
