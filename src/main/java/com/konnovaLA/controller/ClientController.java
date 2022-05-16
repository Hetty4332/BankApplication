package com.konnovaLA.controller;

import com.konnovaLA.model.Client;
import com.konnovaLA.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        clients.addAll(clientService.getClients());
        return clients;
    }

    @PostMapping("/save")
    public String addClient(@Valid Client client) {
        try {
            if (client.getPhoneNumber().isEmpty()) {
                client.setPhoneNumber("Не указан");
            }
            clientService.saveClient(client);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Данные клиента успешно сохранены";
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return "Клиент с id= +" + id + " успешно удален";
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

}
