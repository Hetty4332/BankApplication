package com.konnovaLA.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "person")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    @Transient
    private String passwordConfirm;
    @Column
    private String email;
    @Enumerated(value = EnumType.STRING)
    @Column
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

}
