package com.haulmont.testtask.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
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

}
