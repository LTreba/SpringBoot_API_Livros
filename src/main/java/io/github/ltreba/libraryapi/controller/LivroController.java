package io.github.ltreba.libraryapi.controller;

import io.github.ltreba.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.ltreba.libraryapi.controller.dto.PesquisaLivroDTO;
import io.github.ltreba.libraryapi.controller.mappers.LivroMapper;
import io.github.ltreba.libraryapi.model.GeneroLivro;
import io.github.ltreba.libraryapi.model.Livro;
import io.github.ltreba.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {
    @Autowired
    private LivroService livroService;
    @Autowired
    private LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return(ResponseEntity.created(url).build());
    }


    @GetMapping("{id}")
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterLivroPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return livroService.obterLivroPorId(UUID.fromString(id)).map(livro -> {
            livroService.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<PesquisaLivroDTO>> pesquisa(@RequestParam(value = "titulo", required = true) String titulo, @RequestParam(value = "isbn", required = true) String isbn, @RequestParam(value = "nome-autor", required = true) String nomeaAutor, @RequestParam(value = "genero", required = true) GeneroLivro generoLivro, @RequestParam(value = "ano-publicacao", required = true) Integer anoPublicacao, @RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina){
        var resultado = livroService.pesquisa(titulo, isbn, nomeaAutor, generoLivro, anoPublicacao, pagina, tamanhoPagina);
        Page<PesquisaLivroDTO> resultadoPaginas = resultado.map(livroMapper::toDTO);
        return ResponseEntity.ok(resultadoPaginas);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto){
        return livroService.obterLivroPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidade = livroMapper.toEntity(dto);
                    livro.setDataPublicacao(entidade.getDataPublicacao());
                    livro.setIsbn(entidade.getIsbn());
                    livro.setPreco(entidade.getPreco());
                    livro.setGenero(entidade.getGenero());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());

                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
