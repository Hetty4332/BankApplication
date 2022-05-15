package com.konnovaLA.security;


import com.konnovaLA.model.Role;
import com.konnovaLA.model.Status;
import com.konnovaLA.model.User;
import com.konnovaLA.repository.RoleRepository;
import com.konnovaLA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        return SecurityUser.fromUser(user);
    }


    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByLogin(user.getLogin());

        if (userFromDB != null) {
            return false;
        }
        Role role = roleRepository.findByName("USER");
        user.setRoles(Collections.singleton(role));
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

}
