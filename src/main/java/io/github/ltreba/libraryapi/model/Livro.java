package io.github.ltreba.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livro", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Livro {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    UUID id;
    @Column(name="isbn", length = 20, nullable = false)
    private String isbn;
    @Column(name="titulo", length = 150, nullable = false)
    private String titulo;
    @Column(name="genero", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroLivro genero;
    @Column(name="data_publicacao")
    private LocalDate dataPublicacao;
    @Column(name="preco", precision = 18, scale = 2)
    private BigDecimal preco;
    @ManyToOne
    @JoinColumn(name="id_autor")
    private Autor autor;
    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;
    @CreatedDate
    @Column(name="data_cadastro")
    private LocalDateTime dataCadastro;
    @LastModifiedDate
    @Column(name="data_atualizacao")
    private LocalDateTime dataAtualizacao;
    @JoinColumn(name="usuario")
    private Usuario usuario;

}
