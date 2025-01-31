package com.github.juli220620.service;

import com.github.juli220620.controller.rq.ManageRolesRq;
import com.github.juli220620.controller.rq.UserDataRq;
import com.github.juli220620.model.RoleEntity;
import com.github.juli220620.model.UserEntity;
import com.github.juli220620.repo.UserRepo;
import com.github.juli220620.security.JwtService;
import com.github.juli220620.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public String login(UserDataRq userData) {
        var dbUser = userRepo.findByUsername(userData.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("no such user"));

        if (!passwordEncoder.matches(userData.getPassword(), dbUser.getPassword()))
            throw new IllegalArgumentException("wrong password");

        return jwtService.generateToken(dbUser);
    }

    public void registerUser(UserDataRq userData) {
        var newUser = new UserEntity();

        newUser.setUsername(userData.getUsername());
        newUser.setPassword(passwordEncoder.encode(userData.getPassword()));
        newUser.setRoles(List.of(new RoleEntity(UserRole.USER.name())));
        userRepo.save(newUser);
    }

    public void manageRoles(ManageRolesRq rq) {
        var user = userRepo.findById(rq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("no such user"));

        user.setRoles(new ArrayList<>(rq.getRoles().stream().map(RoleEntity::new).toList()));
        userRepo.save(user);
    }
}
