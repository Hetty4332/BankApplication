package com.haulmont.testtask.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column
    private int creditLimit;
    @Column
    @NotNull
    private double interestRate;
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "credit")
    private List<CreditOffer> creditOffers;

}
