package com.github.juli220620.controller;

import com.github.juli220620.controller.rq.ManageRolesRq;
import com.github.juli220620.controller.rq.UserDataRq;
import com.github.juli220620.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDataRq userData) {
        return userFacade.login(userData);
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserDataRq userData) {
        userFacade.signUp(userData);
        return "Registered successfully, please login";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public void manageRoles(@RequestBody ManageRolesRq rq) {
        userFacade.manageRoles(rq);
    }
}
