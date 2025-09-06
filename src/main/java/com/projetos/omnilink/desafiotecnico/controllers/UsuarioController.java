package com.projetos.omnilink.desafiotecnico.controllers;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final ClienteService clienteService;

    @PostMapping("/criarUsuario")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário que ainda não possua cadastro")
    public ResponseEntity<Object> criarCliente(@RequestBody ClienteCreateDTO clienteDTO) {
        clienteService.criarCliente(clienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente salvo com sucesso!");
    }

    @PutMapping("/alterarUsuario/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualizar dados do usuário")
    public ResponseEntity<?> editarCliente(@PathVariable UUID id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        try {
            clienteService.editarCliente(id, clienteUpdateDTO);
            return ResponseEntity.ok("Cliente atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarUsuario/{id}")
    @Operation(summary = "Buscar usuário", description = "Busca o usuário através do ID informado")
    public ResponseEntity<Object> buscarCliente(@PathVariable String cpf) {
        try {
            Cliente cliente = clienteService.buscarClientePorCpf(cpf);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listarUsuarios")
    @Operation(summary = "Listar usuário", description = "Lista todos os usuário cadastrados no sistema")
    public ResponseEntity<Object> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    @Operation(summary = "Deletar usuário", description = "Deleta o usuário")
    public ResponseEntity<Object> deletarCliente(@PathVariable UUID id) {
        try {
            clienteService.excluirCliente(id);
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
