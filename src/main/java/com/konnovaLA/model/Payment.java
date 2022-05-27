package com.konnovaLA.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate paymentDate;
    @Column
    private double paymentSum;
    @Column
    private double creditBodyRepayment;
    @Column
    private double amountOfInterestRepayment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.paymentSum, paymentSum) == 0 && Double.compare(payment.creditBodyRepayment, creditBodyRepayment) == 0 && Double.compare(payment.amountOfInterestRepayment, amountOfInterestRepayment) == 0 && paymentDate.equals(payment.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentDate, paymentSum, creditBodyRepayment, amountOfInterestRepayment);
    }
}
