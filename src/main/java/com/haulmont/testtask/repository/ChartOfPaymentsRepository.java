package com.haulmont.testtask.repository;

import com.haulmont.testtask.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartOfPaymentsRepository extends JpaRepository<Payment, Long> {
}
