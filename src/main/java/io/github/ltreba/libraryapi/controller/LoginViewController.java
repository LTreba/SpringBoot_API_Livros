package io.github.ltreba.libraryapi.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        return("Olá " + authentication.name());
    }
}
