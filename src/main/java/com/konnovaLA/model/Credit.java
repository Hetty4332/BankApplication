package com.konnovaLA.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
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
    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "credit")
    private List<CreditOffer> creditOffers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credit)) return false;
        Credit credit = (Credit) o;
        return creditLimit == credit.creditLimit && Double.compare(credit.interestRate, interestRate) == 0 && creditOffers.equals(credit.creditOffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditLimit, interestRate, creditOffers);
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", creditLimit=" + creditLimit +
                ", interestRate=" + interestRate +
                '}';
    }
}
