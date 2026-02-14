package io.github.ltreba.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table
@Data
public class Client {
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String clientId;
    @Column(unique = true)
    private String clientSecret;
    @Column
    private String redirectURI;
    @Column
    private String scope;
}
