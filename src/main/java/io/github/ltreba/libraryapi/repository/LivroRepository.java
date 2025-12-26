package io.github.ltreba.libraryapi.repository;

import io.github.ltreba.libraryapi.model.Autor;
import io.github.ltreba.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    @Query("select l from Livro as L order by l.titulo")
    List<Livro> findAll();

    boolean existsByIsbn(String isbn);

    boolean existsByAutor(Autor autor);
}
