package com.projetos.omnilink.desafiotecnico.controllers;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateDTO;
import com.projetos.omnilink.desafiotecnico.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciador de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/criarUsuario")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário que ainda não possua cadastro")
    public ResponseEntity<Object> criarUsuario(@RequestBody UsuarioCreateDTO usuarioDTO) {
        usuarioService.criarUsuario(usuarioDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário salvo com sucesso!");
    }

    @PutMapping("/alterarUsuario/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualizar dados do usuário")
    public ResponseEntity<?> editarUsuario(@PathVariable UUID id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        try {
            usuarioService.editarUsuario(id, usuarioUpdateDTO);
            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/alterarUsuario/senha/{id}")
    @Operation(summary = "Atualizar senha do usuário", description = "Atualizar senha do usuário")
    public ResponseEntity<?> editarSenhaUsuario(@PathVariable UUID id, @RequestBody String senha) {
        try {
            usuarioService.editarSenhaUsuario(id, senha);
            return ResponseEntity.ok("Senha do usuário atualizada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarUsuario/{cpf}")
    @Operation(summary = "Buscar usuário", description = "Busca o usuário através do CPF informado")
    public ResponseEntity<Object> buscarUsuarioPorCpf(@PathVariable String cpf, ServletRequest servletRequest) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpf);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listarUsuarios")
    @Operation(summary = "Listar usuário", description = "Lista todos os usuário cadastrados no sistema")
    public ResponseEntity<Object> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/listarUsuarios/role")
    @Operation(summary = "Listar usuário por Role", description = "Lista todos os usuário cadastrados no sistema com a role informada")
    public ResponseEntity<Object> listarUsuarioPorRole(@RequestParam String role) {
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorRole(role);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    @Operation(summary = "Deletar usuário", description = "Deleta o usuário")
    public ResponseEntity<Object> deletarUsuario(@PathVariable UUID id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
