package com.example.backend.controller;

import com.example.backend.service.UserService;
import com.example.backend.domain.User;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.dto.LoginRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/loginOk")
    public ResponseEntity<Map<String, String>> loginOk() {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String userId = authentication.getName();
        // String authorities = authentication.getAuthorities().toString();

        // System.out.println("로그인한 유저 아이디:" + userId);
        // System.out.println("유저 권한:" + authentication.getAuthorities());

        // Map<String, String> userInfo = new HashMap<>();
        // userInfo.put("userId", userId);
        // userInfo.put("authorities", authorities);

        // return ResponseEntity.ok(userInfo);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = (CustomUserDetails) authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails user = (CustomUserDetails) principal;
            LoginResponseDto dto = new LoginResponseDto(
                    user.getUserId(),
                    user.getName(),
                    user.getRole()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logoutOk")
    public ResponseEntity<Void> logoutOk() {
        System.out.println("로그아웃 성공");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<Void> getAdminPage() {
        System.out.println("어드민 인증 성공");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserPage() {
        System.out.println("일반 인증 성공");

        // 시큐리티에서 읽어서, 해당 정보 유저 반환
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserInfo(email);

        return ResponseEntity.ok(user);
    }

}
