package com.projetos.omnilink.desafiotecnico.repositories;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
