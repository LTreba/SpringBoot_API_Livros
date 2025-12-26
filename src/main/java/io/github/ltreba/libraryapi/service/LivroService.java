package io.github.ltreba.libraryapi.service;

import io.github.ltreba.libraryapi.model.GeneroLivro;
import io.github.ltreba.libraryapi.model.Livro;
import io.github.ltreba.libraryapi.repository.LivroRepository;
import io.github.ltreba.libraryapi.repository.specs.LivroSpecs;
import io.github.ltreba.libraryapi.validador.LivroValidator;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroValidator livroValidator;

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterLivroPorId(UUID Id){
        return(livroRepository.findById(Id));
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String titulo, String isbn, String nomeaAutor, GeneroLivro generoLivro, Integer anoPublicacao, Integer pagina, Integer tamanhoPagina){

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            specs = specs.and(LivroSpecs.isbnIsEquals(isbn));
        }

        if(titulo != null){
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if(generoLivro != null){
            specs = specs.and(LivroSpecs.generoIsEquals(generoLivro));
        }

        if(anoPublicacao != null){
            specs = specs.and(LivroSpecs.anoPublicacaoIsEquals(anoPublicacao));
        }

        if(nomeaAutor != null){
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeaAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return(livroRepository.findAll(specs, pageRequest));
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("O Autor não está salvo na base.");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
