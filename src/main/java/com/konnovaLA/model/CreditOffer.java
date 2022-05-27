package com.konnovaLA.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class CreditOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    private Credit credit;
    @Column
    private int sumCredit;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Payment> chartOfPayments;//график платежей

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditOffer)) return false;
        CreditOffer that = (CreditOffer) o;
        return sumCredit == that.sumCredit && client.equals(that.client) && credit.equals(that.credit) && chartOfPayments.equals(that.chartOfPayments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, credit, sumCredit, chartOfPayments);
    }

    @Override
    public String toString() {
        return "CreditOffer{" +
                "id=" + id +
                ", client=" + client +
                ", credit=" + credit +
                ", sumCredit=" + sumCredit +
                '}';
    }
}
