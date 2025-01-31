package com.github.juli220620.controller.rq;

import lombok.Getter;

import java.util.List;

@Getter
public class ManageRolesRq {

    private Long userId;
    private List<String> roles;
}
