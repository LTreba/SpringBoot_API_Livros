package io.github.ltreba.libraryapi.controller;

import io.github.ltreba.libraryapi.controller.dto.UsuarioDTO;
import io.github.ltreba.libraryapi.controller.mappers.UsuarioMapper;
import io.github.ltreba.libraryapi.model.Usuario;
import io.github.ltreba.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuario) {
        Usuario entity = usuarioMapper.toEntity(usuario);
        usuarioService.salvar(entity);
    }
}
