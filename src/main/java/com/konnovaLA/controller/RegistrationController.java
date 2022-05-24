package com.konnovaLA.controller;


import com.konnovaLA.entities.request.UserRequest;
import com.konnovaLA.mappers.mapperInterface.UserMapper;
import com.konnovaLA.model.ApiUser;
import com.konnovaLA.model.Role;
import com.konnovaLA.repository.RoleRepository;
import com.konnovaLA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/newUser")
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String addUser(@Valid @RequestBody UserRequest request, BindingResult bindingResult) {
        Set<Role> userRoles = new HashSet<>();
        for (String role : request.getNamesRole()) {
            userRoles.add(roleRepository.findByName(role));

        }
        ApiUser user = userMapper.requestToUser(request, userRoles);
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            bindingResult.addError(new FieldError("userForm", "passwordConfirm", "Пароли не совпадают"));
        }
 /*       if (!userService.saveUser(user)) {
            bindingResult.addError(new FieldError("userForm", "username", "Пользователь с таким именем уже существует"));
        }*/
        userRepository.save(user);
        return "Регистрация нового пользователя произошла успешно";
    }
}