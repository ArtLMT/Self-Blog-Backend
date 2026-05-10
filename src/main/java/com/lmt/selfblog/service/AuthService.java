package com.lmt.selfblog.service;

import com.lmt.selfblog.dto.request.LoginRequestDTO;
import com.lmt.selfblog.dto.request.RegisterRequestDTO;
import com.lmt.selfblog.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO register(RegisterRequestDTO request);
}
