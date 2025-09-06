package com.projetos.omnilink.desafiotecnico.repositories;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findUsuariosByRole(String role);
    boolean existsByCpf(String cpf);
}
