package com.konnovaLA.service;

import com.konnovaLA.model.ApiUser;
import com.konnovaLA.repository.UserRepository;
import com.konnovaLA.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public List<ApiUser> getAll() {
        return this.repository.findAll();
    }

    public ApiUser getByLogin(String login) {
        return this.repository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ApiUser u = getByLogin(login);
        if (Objects.isNull(u)) {
            log.error(String.format("User %s is not found", login));
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return SecurityUser.fromUser(u);
    }
}
