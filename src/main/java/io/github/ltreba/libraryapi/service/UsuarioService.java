package io.github.ltreba.libraryapi.service;

import io.github.ltreba.libraryapi.model.Usuario;
import io.github.ltreba.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}
