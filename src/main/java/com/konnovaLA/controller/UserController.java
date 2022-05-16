package com.konnovaLA.controller;

import com.konnovaLA.model.User;
import com.konnovaLA.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public @ResponseBody List<User> getAll() {
        return this.service.getAll();
    }
}