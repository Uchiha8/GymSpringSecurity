package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.AuthenticationRequest;
import com.epam.finalDemo.dto.request.ChangeLoginRequest;
import com.epam.finalDemo.dto.request.RegisterRequest;
import com.epam.finalDemo.service.UserService;
import com.epam.finalDemo.utils.ValidModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final ValidModule validModule;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangeLoginRequest request) {
        try {
            validModule.changePassword(request);
            return ResponseEntity.ok(userService.changePassword(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
