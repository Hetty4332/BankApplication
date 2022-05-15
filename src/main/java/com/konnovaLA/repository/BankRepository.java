package com.konnovaLA.repository;


import com.konnovaLA.model.Bank;
import com.konnovaLA.model.Client;
import com.konnovaLA.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findBankByCreditsContains(Credit credit);

    Optional<Bank> findBankByClientsContains(Client client);

}
