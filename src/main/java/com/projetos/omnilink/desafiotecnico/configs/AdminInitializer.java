package com.projetos.omnilink.desafiotecnico.configs;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Value("${spring.admin.email}")
    private String adminEmail;

    @Value("${spring.admin.password}")
    private String adminPassword;

    @Value("${spring.admin.nome}")
    private String adminNome;

    @Value("${spring.admin.cpf}")
    private String adminCpf;

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
                Usuario admin = Usuario.builder()
                                .nome(adminNome)
                                .cpf(adminCpf)
                                .email(adminEmail)
                                .role(RoleEnum.ADMIN)
                                .senha_hash(passwordEncoder.encode(adminPassword))
                                .build();

                usuarioRepository.save(admin);
            }
        };
    }
}
