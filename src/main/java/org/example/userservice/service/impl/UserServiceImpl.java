package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.AuthResponseDto;
import org.example.userservice.entity.User;
import org.example.userservice.entity.UserType;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.UserService;
import org.example.userservice.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public User save(User user) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserType(UserType.USER);
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public AuthResponseDto login(User user) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail == null || !passwordEncoder.matches(user.getPassword(), byEmail.getPassword())) {
            return null;
        } else {
            return AuthResponseDto.builder()
                    .token(jwtTokenUtil.generateToken(user.getEmail()))
                    .user(byEmail)
                    .build();
        }
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
