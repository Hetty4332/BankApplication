package com.haulmont.testtask.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    @NotBlank
    @Size(max=50)
    private String name;//ФИО клиента
    @Column
    @NotBlank
    private String phoneNumber;
    @Column
    @Email
    private String email;
    @Column
    @NotBlank
    @Size(max=10,min = 10)
    private String passportNumber;

}
