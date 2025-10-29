package com.vornicu.user_service.auth;


import com.vornicu.user_service.refreshToken.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegistrationRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid LoginRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/activate-account")
    public ResponseEntity<?> activate(@RequestParam String token){
        authService.activateAccount(token);
        return ResponseEntity.ok("Account activated successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

}
