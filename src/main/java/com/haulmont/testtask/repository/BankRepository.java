package com.haulmont.testtask.repository;


import com.haulmont.testtask.model.Bank;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findBankByCreditsContains(Credit credit);

    Optional<Bank> findBankByClientsContains(Client client);

}
