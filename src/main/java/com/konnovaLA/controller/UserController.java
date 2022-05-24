package com.konnovaLA.controller;

import com.konnovaLA.model.ApiUser;
import com.konnovaLA.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomUserDetailsService service;

    @GetMapping("/users")
    public @ResponseBody List<ApiUser> getAll() {
        return this.service.getAll();
    }
}