package io.github.ltreba.libraryapi.service;

import io.github.ltreba.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.ltreba.libraryapi.model.Autor;
import io.github.ltreba.libraryapi.repository.AutorRepository;
import io.github.ltreba.libraryapi.repository.LivroRepository;
import io.github.ltreba.libraryapi.validador.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    @Autowired
    private AutorRepository repository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private final AutorValidator autorValidator = new AutorValidator();

    public Autor salvar(Autor autor){
        autorValidator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("O Autor não está salvo na base.");
        }
        autorValidator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id){
        return(repository.findById(id));
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Autor possui livros cadastrados.");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }else if(nome != null && nacionalidade == null){
            return repository.findByNome(nome);
        }else if(nome == null && nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

    public boolean possuiLivro(Autor autor){
        return(livroRepository.existsById(autor.getId()));
    }
}
