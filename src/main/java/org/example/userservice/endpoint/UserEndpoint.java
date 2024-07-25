package org.example.userservice.endpoint;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.AuthResponseDto;
import org.example.userservice.entity.User;
import org.example.userservice.security.CurrentUser;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserEndpoint {

    private final UserService userService;


    @PostMapping("/register")
    private ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponseDto> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user));
    }

    @GetMapping("/currentUser")
    private ResponseEntity<User> getUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(currentUser.getUser());
    }

    @GetMapping("/user/{email}")
    private ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

}
