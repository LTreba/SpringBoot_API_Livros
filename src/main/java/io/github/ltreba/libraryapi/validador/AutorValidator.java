package io.github.ltreba.libraryapi.validador;

import io.github.ltreba.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ltreba.libraryapi.model.Autor;
import io.github.ltreba.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    @Autowired
    private AutorRepository autorRepository;

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado.");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorCadastrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if(autor.getId() == null){
            return autorCadastrado.isPresent();
        }

        return(autor.getId().equals(autorCadastrado.get().getId()) && autorCadastrado.isPresent());
    }
}
