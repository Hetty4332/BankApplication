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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public List<ApiUser> getAll() {
        return this.repository.findAll();
    }

    public Optional<ApiUser> getByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ApiUser u = getByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s is not found", login)));
        return SecurityUser.fromUser(u);
    }
}
