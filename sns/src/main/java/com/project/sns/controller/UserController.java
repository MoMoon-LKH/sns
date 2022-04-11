package com.project.sns.controller;

import com.project.sns.domain.User;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(
            @Valid @RequestBody UserDto userDto
    ) {
        userService.signUp(userDto);

        return ResponseEntity.ok(userService.findOneWithEmail(userDto.getEmail()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(value = "userId") Long userId) {
        return ResponseEntity.ok(userService.findOneWithId(userId));
    }

}
