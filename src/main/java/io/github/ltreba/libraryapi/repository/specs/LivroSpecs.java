package io.github.ltreba.libraryapi.repository.specs;

import io.github.ltreba.libraryapi.model.GeneroLivro;
import io.github.ltreba.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnIsEquals(String isbn){
        return(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        return(root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")),"%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoIsEquals(GeneroLivro genero){
        return(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoIsEquals(Integer anoPublicacao){
        return(root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("to_char", String.class, root.get("dataPublicacao"), criteriaBuilder.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome){
        return(root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")),"%" + nome.toUpperCase() + "%");
        };
    }
}
