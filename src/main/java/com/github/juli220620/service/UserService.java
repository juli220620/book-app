package com.github.juli220620.service;

import com.github.juli220620.controller.rq.UserDataRq;
import com.github.juli220620.repo.UserRepo;
import com.github.juli220620.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final JwtService jwtService;
    private final UserRepo userRepo;


    public String login(UserDataRq userData) {
        return "put jwt token here";
    }
}
