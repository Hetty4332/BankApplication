package com.konnovaLA.mappers.mapperInterface;

import com.konnovaLA.model.ApiUser;
import com.konnovaLA.model.Role;
import com.konnovaLA.entities.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "userRoles")
    ApiUser requestToUser(UserRequest request, Set<Role> userRoles);

}
