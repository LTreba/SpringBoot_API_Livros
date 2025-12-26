package io.github.ltreba.libraryapi.controller.dto;

import io.github.ltreba.libraryapi.model.GeneroLivro;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PesquisaLivroDTO(UUID id, String isbn, String titulo, LocalDate dataPublicacao, GeneroLivro genero, BigDecimal preco, AutorDTO autor) {
}
