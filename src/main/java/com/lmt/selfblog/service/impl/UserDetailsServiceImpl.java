package com.lmt.selfblog.service.impl;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.entity.CustomUserDetails;
import com.lmt.selfblog.entity.User;
import com.lmt.selfblog.exception.NotFoundException;
import com.lmt.selfblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("Username cannot be null");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}

