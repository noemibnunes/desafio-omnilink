package com.projetos.omnilink.desafiotecnico.controllers;

import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;
import com.projetos.omnilink.desafiotecnico.services.VeiculoService;
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
    public ResponseEntity<Object> criarVeiculo(@RequestBody VeiculoCreateDTO veiculoDTO) {
        veiculoService.criarVeiculo(veiculoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Veículo salvo com sucesso!");
    }

    @PutMapping("/alterarVeiculo/{id}")
    @Operation(summary = "Atualizar veículo", description = "Atualizar dados do veículo")
    public ResponseEntity<?> editarVeiculo(@PathVariable UUID id, @RequestBody VeiculoUpdateDTO veiculoUpdateDTO) {
        try {
            veiculoService.editarVeiculo(id, veiculoUpdateDTO);
            return ResponseEntity.ok("Veículo atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarVeiculo/{chassi}")
    @Operation(summary = "Buscar veículo", description = "Busca o veículo através do Chassi informado")
    public ResponseEntity<Object> buscarVeiculoPeloChassi(@PathVariable String chassi) {
        try {
            Veiculo veiculo = veiculoService.buscarVeiculoPeloChassi(chassi);
            return ResponseEntity.ok(veiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listarVeiculos")
    @Operation(summary = "Listar veículo", description = "Lista todos os veículos cadastrados no sistema")
    public ResponseEntity<Object> listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarVeiculos();

        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(veiculos);
    }

    @DeleteMapping("/deletarVeiculo/{id}")
    @Operation(summary = "Deletar veículo", description = "Deleta o veículo")
    public ResponseEntity<Object> deletarVeiculo(@PathVariable UUID id) {
        try {
            veiculoService.excluirVeiculo(id);
            return ResponseEntity.ok("Veículo deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
