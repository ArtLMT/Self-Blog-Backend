package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.dto.request.LoginRequestDTO;
import com.lmt.selfblog.dto.request.RegisterRequestDTO;
import com.lmt.selfblog.dto.response.AuthResponseDTO;
import com.lmt.selfblog.exception.ConflictException;
import com.lmt.selfblog.exception.UnauthorizedException;
import com.lmt.selfblog.model.Role;
import com.lmt.selfblog.model.User;
import com.lmt.selfblog.repository.UserRepository;
import com.lmt.selfblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UnauthorizedException(
                                ErrorCode.INVALID_CREDENTIALS,
                                "Username not found: " + request.getUsername()
                        )
                );

        if (!request.getPassword().equals(user.getPassword())) {

            throw new UnauthorizedException(
                    ErrorCode.INVALID_CREDENTIALS,
                    "Wrong password for username: " + request.getUsername()
            );
        }

        String token = "stub-jwt-token-for-" + user.getUsername();

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {

            throw new ConflictException(
                    ErrorCode.USERNAME_ALREADY_EXISTS,
                    "Duplicate username: " + request.getUsername()
            );
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new ConflictException(
                    ErrorCode.EMAIL_ALREADY_EXISTS,
                    "Duplicate email: " + request.getEmail()
            );
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())

                .password(request.getPassword())

                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = "stub-jwt-token-for-" + user.getUsername();

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
