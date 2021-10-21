package com.haulmont.testtask.service;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BankService bankService;
    @Autowired
    CreditOfferService creditOfferService;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }
    public void saveClient(Client client) {
       clientRepository.save(client);
    }
    public void deleteClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return;
        }
        bankService.deleteClientInBank(client.get());
        creditOfferService.deleteByUser(id);
        clientRepository.deleteById(id);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(new Client());
    }
}
