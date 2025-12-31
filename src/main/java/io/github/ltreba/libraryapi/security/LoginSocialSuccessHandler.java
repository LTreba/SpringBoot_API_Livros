package io.github.ltreba.libraryapi.security;

import io.github.ltreba.libraryapi.model.Usuario;
import io.github.ltreba.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService usuarioService;

    private static final String SENHA_PADRAO = "321";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = oauth2AuthenticationToken.getPrincipal();
        String email = principal.getAttribute("email").toString();

        Usuario usuario = usuarioService.obterPorEmail(email);

        if(usuario == null){
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setLogin(email.substring(0,email.indexOf("@")));
            usuario.setSenha(SENHA_PADRAO);
            usuario.setRoles(List.of("OPERADOR"));
            usuarioService.salvar(usuario);
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, customAuthentication);
    }

}
