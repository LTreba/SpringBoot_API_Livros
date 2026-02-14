package io.github.ltreba.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.ltreba.libraryapi.model.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}
