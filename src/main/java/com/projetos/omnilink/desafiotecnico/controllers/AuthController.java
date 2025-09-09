package com.projetos.omnilink.desafiotecnico.controllers;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import com.projetos.omnilink.desafiotecnico.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name= "Login", description = "Autenticação do usuário")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentique-se para acessar as operações do sistema")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha_hash())) {
            return ResponseEntity.status(401).body("Email ou Senha inválidos");
        }

        String token = jwtUtil.generateToken(usuario.getId(), usuario.getRole());
        return ResponseEntity.ok(token);
    }
}
