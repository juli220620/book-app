package com.github.juli220620.facade;

import com.github.juli220620.controller.rq.ManageRolesRq;
import com.github.juli220620.controller.rq.UserDataRq;
import com.github.juli220620.security.UserRole;
import com.github.juli220620.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public ResponseEntity<String> login(UserDataRq userData) {
        validateData(userData);

        var jwt = userService.login(userData);

        var headers = new HttpHeaders();
        headers.setBearerAuth(jwt);

        return new ResponseEntity<>("login successful", headers, HttpStatus.OK);
    }

    public void signUp(UserDataRq userData) {
        validateData(userData);

        userService.registerUser(userData);
    }

    public void manageRoles(ManageRolesRq rq) {
        validateRoles(rq.getRoles());
        userService.manageRoles(rq);
    }

    private void validateRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) throw new IllegalArgumentException("user must have at least one role");

        roles.retainAll(Arrays.stream(UserRole.values()).map(Enum::name).toList());

        if (roles.isEmpty()) throw new IllegalArgumentException("no such roles");
    }

    private void validateData(UserDataRq userData) {
        if (userData.getUsername() == null
                || userData.getUsername().isBlank()
                || userData.getPassword() == null
                || userData.getPassword().isBlank()
        ) throw new IllegalArgumentException("provide username and password");
    }
}
