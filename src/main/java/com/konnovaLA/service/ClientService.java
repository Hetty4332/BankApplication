package com.konnovaLA.service;

import com.konnovaLA.model.Client;
import com.konnovaLA.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    private BankService bankService;

    private CreditOfferService creditOfferService;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    @Autowired
    public void setCreditOfferService(CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void deleteClientById(Long id) {
        clientRepository.findById(id)
                .ifPresent(client ->
                {
                    bankService.deleteClientInBank(client);
                    creditOfferService.deleteByUser(id);
                    clientRepository.deleteById(id);
                });

    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
}
