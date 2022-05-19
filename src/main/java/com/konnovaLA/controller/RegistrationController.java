package com.konnovaLA.controller;


import com.konnovaLA.model.User;
import com.konnovaLA.repository.UserRepository;
import com.konnovaLA.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserDetailsServiceImpl userService;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newUser")
    public String addUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.addError(new FieldError("userForm", "passwordConfirm", "Пароли не совпадают"));
        }
        if (!userService.saveUser(user)) {
            bindingResult.addError(new FieldError("userForm", "username", "Пользователь с таким именем уже существует"));
        }
        userRepository.save(user);
        return "Регистрация нового пользователя произошла успешно";
    }
}