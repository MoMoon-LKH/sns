package com.project.sns.service;

import com.project.sns.domain.Authority;
import com.project.sns.domain.User;
import com.project.sns.domain.dto.UserDto;
import com.project.sns.repository.AuthorityRepository;
import com.project.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;


    @Transactional
    public Long signUp(UserDto userDto) {
        Authority userAuthority = authorityRepository.getUserAuthority();

        User user = convertUserDtoToUser(userDto, userAuthority);

        return userRepository.save(user);
    }


    public Optional<User> findOneToEmail(String email) {

        return userRepository.findOneToEmail(email);
    }


    public String login(User user) {
        return "";
    }


    private User convertUserDtoToUser(UserDto userDto, Authority authority) {
        String encodePw = passwordEncoder.encode(userDto.getPw());

        return User.createUser(
                userDto.getEmail(),
                encodePw,
                userDto.getNickname(),
                userDto.getGender(),
                authority
                );
    }


}
