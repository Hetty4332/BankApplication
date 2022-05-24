package com.konnovaLA.repository;

import com.konnovaLA.model.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<ApiUser, Long>{
       Optional<ApiUser>  findByLogin (String username);
}
