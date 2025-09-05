package com.projetos.omnilink.desafiotecnico.repositories;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByNome(String nome);
    Optional<Cliente> findByCpf(String cpf);
    boolean existsByCpf(String cpf);

}
