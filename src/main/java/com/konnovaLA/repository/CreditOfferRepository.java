package com.konnovaLA.repository;

import com.konnovaLA.model.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
    Optional<CreditOffer> findByClient_Id(Long id);

    Optional<CreditOffer> findByCredit_Id(Long id);
}
