package com.haulmont.testtask.repository;


import com.haulmont.testtask.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {
}
