package com.konnovaLA.controller;

import com.konnovaLA.entities.request.ClientRequest;
import com.konnovaLA.mappers.ClientMapper;
import com.konnovaLA.model.Client;
import com.konnovaLA.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PostMapping("/save")
    public String addClient(@Valid ClientRequest client) {
        try {
            clientService.saveClient(clientMapper.dtoToClient(client));
        } catch (Exception e) {
            return e.getMessage();// TODO:переделать обработку
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
        return clientService.getClientById(id).orElseThrow(NumberFormatException::new);
    }

}
