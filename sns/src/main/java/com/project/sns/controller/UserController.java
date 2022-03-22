package com.project.sns.controller;

import com.project.sns.domain.User;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(
            @Valid @RequestBody UserDto userDto
    ) {
        userService.signUp(userDto);

        return ResponseEntity.ok(userService.findOneWithEmail(userDto.getEmail()).orElseGet(null));
    }

}
