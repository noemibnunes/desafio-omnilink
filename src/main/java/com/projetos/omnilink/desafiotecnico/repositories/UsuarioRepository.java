package com.projetos.omnilink.desafiotecnico.repositories;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findUsuariosByRole(RoleEnum role);
    boolean existsByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
}
