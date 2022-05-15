package com.konnovaLA.repository;

import com.konnovaLA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{
        User findByLogin (String username);
}
