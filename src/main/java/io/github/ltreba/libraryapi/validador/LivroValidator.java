package io.github.ltreba.libraryapi.validador;

import io.github.ltreba.libraryapi.exceptions.CampoInvalidoException;
import io.github.ltreba.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ltreba.libraryapi.model.Livro;
import io.github.ltreba.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    @Autowired
    private LivroRepository livroRepository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro.getIsbn())){
            throw new RegistroDuplicadoException("ISBN já cadastrado.");
        }

        if(isPrecoObrigadotioNulo(livro.getPreco(), livro.getDataPublicacao())){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório");
        }
    }

    private boolean existeLivroComIsbn(String isbn){
        return(livroRepository.existsByIsbn(isbn));
    }

    private boolean isPrecoObrigadotioNulo(BigDecimal preco, LocalDate data){
        return(preco == null && data.getYear() >= 2020);
    }
}
