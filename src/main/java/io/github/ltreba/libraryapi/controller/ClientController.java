package io.github.ltreba.libraryapi.controller;

import io.github.ltreba.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.ltreba.libraryapi.model.Client;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public void save(@RequestBody Client client) {
        log.info("Iniciando client salvar");
        clientService.salvar(client);
    }
}
