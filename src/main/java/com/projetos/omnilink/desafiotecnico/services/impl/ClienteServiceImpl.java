package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import com.projetos.omnilink.desafiotecnico.exception.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.repositories.ClienteRepository;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import com.projetos.omnilink.desafiotecnico.services.ClienteService;
import com.projetos.omnilink.desafiotecnico.utils.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String SOMENTE_NUMEROS = "[^\\d]";

    @Override
    public void criarCliente(ClienteCreateDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setCpf(clienteDTO.getCpf().replaceAll(SOMENTE_NUMEROS, ""));
        cliente.setDataNascimento(clienteDTO.getDataNascimento());

        verificarDuplicadoPorCpf(cliente.getCpf());
        ClienteValidator.verificarDadosCliente(cliente);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Usuario usuario = Usuario.builder()
                .cliente(clienteSalvo)
                .nome(clienteSalvo.getNome())
                .email(clienteSalvo.getEmail())
                .senha_hash(passwordEncoder.encode(clienteSalvo.getCpf()))
                .role(RoleEnum.ROLE_CLIENTE)
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        clienteSalvo.setUsuario(usuarioSalvo);
        clienteRepository.save(clienteSalvo);
    }

    @Override
    public void editarCliente(UUID id, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (dto.getNome() != null) cliente.setNome(dto.getNome());
        if (dto.getEmail() != null) cliente.setEmail(dto.getEmail());
        if (dto.getDataNascimento() != null) cliente.setDataNascimento(dto.getDataNascimento());

        clienteRepository.save(cliente);

        Usuario usuario = cliente.getUsuario();
        if (usuario != null) {
            if (dto.getNome() != null) usuario.setNome(dto.getNome());
            if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public Optional<Cliente> buscarClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void excluirCliente(UUID idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Usuario usuario = cliente.getUsuario();
        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }

        clienteRepository.delete(cliente);
    }

    public void verificarDuplicadoPorCpf(String cpf) {
        boolean existe = clienteRepository.existsByCpf(cpf);

        if (existe) {
            throw new IllegalArgumentException("Já existe um cliente com esse CPF informado.");
        }
    }
}
