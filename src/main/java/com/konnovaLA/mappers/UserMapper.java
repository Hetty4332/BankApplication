
package com.konnovaLA.mappers;


import com.konnovaLA.request.UserRequest;
import org.springframework.security.core.userdetails.User;

public class UserMapper {
    public User mapToUserDto(UserRequest userRequest)
    {
        UserRequest.builder()
                .login()
    }
}

