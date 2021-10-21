package com.haulmont.testtask.repository;

import com.haulmont.testtask.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
