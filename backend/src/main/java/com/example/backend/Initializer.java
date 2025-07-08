package com.example.backend;

import com.example.backend.domain.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Initializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User admin = User.builder()
                .userId("admin")
                .password(passwordEncoder.encode("admin"))
                .name("관리자")
                .role("A")
                .regUser("SYSTEM")
                .build();

        User user = User.builder()
                .userId("user")
                .password(passwordEncoder.encode("user"))
                .name("사용자")
                .role("G")
                .regUser("SYSTEM")
                .build();

        userRepository.save(admin);
        userRepository.save(user);
    }
}
