package com.projetos.omnilink.desafiotecnico.controllers;

import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciador de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/criarCliente")
    @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente que ainda não possua cadastro")
    public ResponseEntity<Object> criarCliente(@RequestBody ClienteCreateDTO clienteDTO) {
        clienteService.criarCliente(clienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente salvo com sucesso!");
    }

    @PutMapping("/alterarCliente/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualizar dados do cliente")
    public ResponseEntity<?> editarCliente(@PathVariable UUID id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        try {
            clienteService.editarCliente(id, clienteUpdateDTO);
            return ResponseEntity.ok("Cliente atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarCliente/{cpf}")
    @Operation(summary = "Buscar cliente", description = "Busca o cliente através do CPF informado")
    public ResponseEntity<Object> buscarCliente(@PathVariable String cpf) {
        try {
            Optional<Cliente> cliente = clienteService.buscarClientePorCpf(cpf);
            if (cliente.isPresent()) {
                return ResponseEntity.ok(cliente.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cliente não encontrado");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listarClientes")
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes cadastrados no sistema")
    public ResponseEntity<Object> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @DeleteMapping("/deletarCliente/{id}")
    @Operation(summary = "Deletar cliente", description = "Deleta o cliente")
    public ResponseEntity<Object> deletarCliente(@PathVariable UUID id) {
        try {
            clienteService.excluirCliente(id);
            return ResponseEntity.ok("Cliente deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
