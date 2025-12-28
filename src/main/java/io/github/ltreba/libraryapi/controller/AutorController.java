package io.github.ltreba.libraryapi.controller;

import io.github.ltreba.libraryapi.controller.dto.AutorDTO;
import io.github.ltreba.libraryapi.controller.mappers.AutorMapper;
import io.github.ltreba.libraryapi.model.Autor;
import io.github.ltreba.libraryapi.model.Usuario;
import io.github.ltreba.libraryapi.service.AutorService;
import io.github.ltreba.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController implements GenericController {

    @Autowired
    private AutorService autorService;
    @Autowired
    private AutorMapper autorMapper;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> salvarAutor(@RequestBody @Valid AutorDTO dto, Authentication authentication) {

        UserDetails usuarioLogado =  (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioService.obterPorLogin(usuarioLogado.getUsername());

        Autor autor = autorMapper.toEntity(dto);
        autor.setUsuario(usuario);
        autorService.salvar(autor);
        var url = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> getAutor(@PathVariable String id) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autor = autorService.buscarPorId(idAutor);
        if (autor.isPresent()) {
            Autor entidade = autor.get();
            AutorDTO dto = autorMapper.toDTO(entidade);
            return ResponseEntity.ok(dto);
        }
        return (ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autor = autorService.buscarPorId(idAutor);
        if (autor.isEmpty()) {
            return (ResponseEntity.notFound().build());
        }

        autorService.deletar(autor.get());
        return (ResponseEntity.noContent().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(required = false) String nome, @RequestParam(required = false) String nacionalidade) {
        List<Autor> autores = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> autoresDTOs = autores.stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());

        return (ResponseEntity.ok(autoresDTOs));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> atualizarAutor(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autor = autorService.buscarPorId(idAutor);
        if (autor.isEmpty()) {
            return (ResponseEntity.notFound().build());
        }

        var autorResponse = autor.get();
        autorResponse.setDataNascimento(dto.dataNascimento());
        autorResponse.setNacionalidade(dto.nacionalidade());
        autorResponse.setNome(dto.nome());

        autorService.atualizar(autorResponse);

        return (ResponseEntity.noContent().build());
    }
}
