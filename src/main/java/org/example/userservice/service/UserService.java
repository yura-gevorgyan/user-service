package org.example.userservice.service;

import org.example.userservice.dto.AuthResponseDto;
import org.example.userservice.entity.User;

public interface UserService {
    User save(User user);

    AuthResponseDto login(User user);

    User getByEmail(String email);
}
