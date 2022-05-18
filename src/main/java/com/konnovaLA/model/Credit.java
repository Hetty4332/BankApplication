package com.konnovaLA.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
