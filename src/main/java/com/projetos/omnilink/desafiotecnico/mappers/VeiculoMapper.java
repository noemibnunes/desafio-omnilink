package com.projetos.omnilink.desafiotecnico.mappers;

import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public Veiculo toEntity(VeiculoCreateDTO dto) {
        return Veiculo.builder()
                .chassi(dto.getChassi())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .ano(dto.getAno())
                .tipoCombustivel(dto.getTipoCombustivel())
                .tipoVeiculo(dto.getTipoVeiculo())
                .quilometragem(dto.getQuilometragem())
                .observacoes(dto.getObservacoes())
                .build();
    }

    public void updateVeiculoFromDto(Veiculo veiculo, VeiculoUpdateDTO dto) {
        veiculo.setQuilometragem(dto.getQuilometragem());
        veiculo.setObservacoes(dto.getObservacoes());
    }
}
