package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.mappers.ClienteMapper;
import com.projetos.omnilink.desafiotecnico.repositories.ClienteRepository;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import com.projetos.omnilink.desafiotecnico.services.ClienteService;
import com.projetos.omnilink.desafiotecnico.utils.ClienteValidator;
import com.projetos.omnilink.desafiotecnico.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteMapper clienteMapper;

    @Override
    public void criarCliente(ClienteCreateDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        cliente.setCpf(Utils.normalizarCpf(cliente.getCpf()));

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
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Cliente não encontrado."));

        clienteMapper.updateClienteFromDto(cliente, dto);
        clienteRepository.save(cliente);

        Usuario usuario = cliente.getUsuario();
        if (usuario != null) {
            clienteMapper.updateUsuarioFromCliente(usuario, dto);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(Utils.normalizarCpf(cpf))
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Cliente não encontrado."));
    }


    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void excluirCliente(UUID idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Cliente não encontrado."));

        Usuario usuario = cliente.getUsuario();
        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }

        clienteRepository.delete(cliente);
    }

    public void verificarDuplicadoPorCpf(String cpf) {
        boolean existe = clienteRepository.existsByCpf(Utils.normalizarCpf(cpf));

        if (existe) {
            throw new RegistroDuplicadoException("Já existe um cliente com esse CPF informado.");
        }
    }
}
