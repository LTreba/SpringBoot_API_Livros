package io.github.ltreba.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column
    private String login;
    @Column
    private String senha;
    @Column
    private String email;
    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
