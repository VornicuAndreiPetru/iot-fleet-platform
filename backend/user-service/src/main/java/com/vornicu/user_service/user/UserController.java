package com.vornicu.user_service.user;


import com.vornicu.user_service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user);
    }
}
