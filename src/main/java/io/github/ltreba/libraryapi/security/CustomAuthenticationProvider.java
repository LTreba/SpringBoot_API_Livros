package io.github.ltreba.libraryapi.security;

import io.github.ltreba.libraryapi.model.Usuario;
import io.github.ltreba.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senha = authentication.getCredentials().toString();


        Usuario usuario = usuarioService.obterPorLogin(login);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário/Senha incorretos.");
        }

        String senhaCriptografada = usuario.getSenha();
        boolean senhaConfere = passwordEncoder.matches(senha, senhaCriptografada);

        if(senhaConfere) {
            return new CustomAuthentication(usuario);
        }

        throw new UsernameNotFoundException("Usuário/Senha incorretos.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(CustomAuthentication.class);
    }
}
