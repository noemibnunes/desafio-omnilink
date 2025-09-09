package com.projetos.omnilink.desafiotecnico.controllers;

import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;
import com.projetos.omnilink.desafiotecnico.services.VeiculoService;
import com.projetos.omnilink.desafiotecnico.utils.MensagemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Gerenciador de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping("/criarVeiculo")
    @Operation(summary = "Cadastrar veículo", description = "Cadastra um novo veículo")
    public ResponseEntity<MensagemResponse> criarVeiculo(@RequestBody VeiculoCreateDTO veiculoDTO) {
        veiculoService.criarVeiculo(veiculoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MensagemResponse("Veículo salvo com sucesso!"));
    }

    @PutMapping("/alterarVeiculo/{id}")
    @Operation(summary = "Atualizar veículo", description = "Atualizar dados do veículo")
    public ResponseEntity<MensagemResponse> editarVeiculo(@PathVariable UUID id, @RequestBody VeiculoUpdateDTO veiculoUpdateDTO) {
        try {
            veiculoService.editarVeiculo(id, veiculoUpdateDTO);
            return ResponseEntity.ok(new MensagemResponse("Veículo atualizado com sucesso."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensagemResponse(e.getMessage()));
        }
    }

    @GetMapping("/buscarVeiculo/{chassi}")
    @Operation(summary = "Buscar veículo", description = "Busca o veículo através do Chassi informado")
    public ResponseEntity<Veiculo> buscarVeiculoPeloChassi(@PathVariable String chassi) {
        try {
            Veiculo veiculo = veiculoService.buscarVeiculoPeloChassi(chassi);
            return ResponseEntity.ok(veiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/listarVeiculos")
    @Operation(summary = "Listar veículo", description = "Lista todos os veículos cadastrados no sistema")
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarVeiculos();

        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(veiculos);
    }

    @PostMapping("/cliente/{clienteId}")
    @Operation(summary = "Criar veículo para um determinado cliente", description = "Criar veiculo para cliente")
    public ResponseEntity<Veiculo> criarVeiculoParaCliente(@PathVariable UUID clienteId, @RequestBody VeiculoCreateDTO veiculoDTO) {
        Veiculo veiculo = veiculoService.criarVeiculoParaCliente(clienteId, veiculoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
    }

    @PutMapping("/atualizarCliente/{clienteId}/{veiculoId}")
    @Operation(summary = "Atualizar cliente do veículo", description = "Atualizar cliente do veículo")
    public ResponseEntity<MensagemResponse> atualizarClienteDoVeiculo(@PathVariable UUID clienteId, @PathVariable UUID veiculoId) {
        try {
            veiculoService.atualizarClienteDoVeiculo(clienteId, veiculoId);
            return ResponseEntity.ok(new MensagemResponse("Veículo atualizado com o novo cliente com sucesso."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensagemResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/deletarVeiculo/{id}")
    @Operation(summary = "Deletar veículo", description = "Deleta o veículo")
    public ResponseEntity<MensagemResponse> deletarVeiculo(@PathVariable UUID id) {
        try {
            veiculoService.excluirVeiculo(id);
            return ResponseEntity.ok(new MensagemResponse("Veículo deletado com sucesso."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensagemResponse(e.getMessage()));
        }
    }
}
