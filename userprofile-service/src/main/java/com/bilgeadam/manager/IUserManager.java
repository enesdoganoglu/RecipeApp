package com.bilgeadam.manager;

import com.bilgeadam.dto.request.ToAuthPasswordChangeDto;
import com.bilgeadam.dto.request.UpdateUserInformationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.CacheRequest;
import java.util.List;

@FeignClient(url = "http://localhost:8090/api/v1/user", name = "userprofile-user",decode404 = true)
public interface IUserManager {
    @PutMapping("/update-user-information")
    public ResponseEntity<Boolean> updateUsernameOrEmail(@RequestBody UpdateUserInformationRequestDto dto);

    @PutMapping("/password-change")
    public ResponseEntity<Boolean> passwordChange(@RequestBody ToAuthPasswordChangeDto dto);

    @GetMapping("/find-by-role/{role}")
    public ResponseEntity<List<Long>> findByRole(@PathVariable String role);
}
