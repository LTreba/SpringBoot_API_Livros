package io.github.ltreba.libraryapi.controller.dto;

import io.github.ltreba.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastroLivroDTO(@NotBlank(message = "Campo obrigatório") @ISBN String isbn, @NotBlank(message = "Campo obrigatório") String titulo, @NotNull(message = "Campo obrigatório") @Past(message = "Não pode ser uma data futura") LocalDate dataPublicacao, GeneroLivro genero, BigDecimal preco, @NotNull(message = "Campo obrigatório") UUID idAutor) {
}
