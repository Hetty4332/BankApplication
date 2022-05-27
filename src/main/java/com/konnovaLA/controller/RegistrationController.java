package com.konnovaLA.controller;


import com.konnovaLA.entities.request.UserRequest;
import com.konnovaLA.mappers.UserMapper;
import com.konnovaLA.model.ApiUser;
import com.konnovaLA.model.Role;
import com.konnovaLA.repository.RoleRepository;
import com.konnovaLA.repository.UserRepository;
import com.konnovaLA.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/newUser")
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRequest request) {
        String message = userService.addUser(request);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}