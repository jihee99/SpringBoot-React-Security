package com.example.backend.security;

import com.example.backend.domain.User;
import com.example.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("넘어온 userId : " + userId);
        System.out.println("loadUserByUsername 실행");

        // 사용자 조회, 없으면 예외 발생
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }

}