package com.projetos.omnilink.desafiotecnico.repositories;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    boolean existsByChassi(String chassi);
    Optional<Veiculo> findByChassi(String chassi);
    List<Veiculo> findVeiculosByCliente(Cliente cliente);
}
