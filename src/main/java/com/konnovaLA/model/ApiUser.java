package com.konnovaLA.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "person")
public class ApiUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String login;
    private String password;
    @Transient
    private String passwordConfirm;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiUser)) return false;
        ApiUser apiUser = (ApiUser) o;
        return login.equals(apiUser.login) && password.equals(apiUser.password) && passwordConfirm.equals(apiUser.passwordConfirm) && email.equals(apiUser.email) && status == apiUser.status && roles.equals(apiUser.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, passwordConfirm, email, status, roles);
    }

    @Override
    public String toString() {
        return "ApiUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
