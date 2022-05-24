package com.konnovaLA.repository;

import com.konnovaLA.model.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<ApiUser, Long>{
        ApiUser findByLogin (String username);
}
