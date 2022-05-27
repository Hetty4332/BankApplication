package com.konnovaLA.service;

import com.konnovaLA.entities.request.UserRequest;
import com.konnovaLA.mappers.UserMapper;
import com.konnovaLA.model.ApiUser;
import com.konnovaLA.model.Role;
import com.konnovaLA.repository.RoleRepository;
import com.konnovaLA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public String addUser(UserRequest request) {

        String message;
        Set<Role> userRoles = new HashSet<>();
        for (String role : request.getNamesRole()) {
            userRoles.add(roleRepository.findByName(role));
        }
        ApiUser user = userMapper.requestToUser(request, userRoles);
        if (!request.getPassword()
                .equals(request.getPasswordConfirm())) {
            message = "Пароли не совпадают";
        } else if (userRepository.findByLogin(user.getLogin()).isEmpty()) {
            message = "Пользователь с таким именем уже существует";
        } else {
            userRepository.save(user);
            message = "Регистрация нового пользователя произошла успешно";
        }
        return message;
    }

}
