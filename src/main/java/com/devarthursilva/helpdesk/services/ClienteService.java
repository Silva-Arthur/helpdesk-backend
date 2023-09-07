package com.devarthursilva.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devarthursilva.helpdesk.domain.Cliente;
import com.devarthursilva.helpdesk.domain.Pessoa;
import com.devarthursilva.helpdesk.domain.dtos.ClienteDTO;
import com.devarthursilva.helpdesk.repositories.ClienteRepository;
import com.devarthursilva.helpdesk.repositories.PessoaRepository;
import com.devarthursilva.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.devarthursilva.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		validaProCpfEEmail(clienteDTO);
		
		Cliente cliente = new Cliente(clienteDTO);
		return clienteRepository.save(cliente);
	}

	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		Cliente oldCliente = findById(id);
		
		if (!clienteDTO.getSenha().equals(oldCliente.getSenha())) {
			clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		}
		
		
		validaProCpfEEmail(clienteDTO);
		oldCliente = new Cliente(clienteDTO);
		return clienteRepository.save(oldCliente);
	}
	

	public void delete(Integer id) {
		Cliente cliente = findById(id);
		
		if (cliente.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		} 
		
		clienteRepository.deleteById(id);
	}
	
	private void validaProCpfEEmail(ClienteDTO clienteDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(clienteDTO.getCpf());
		
		if (obj.isPresent() && obj.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(clienteDTO.getEmail());
		
		if (obj.isPresent() && obj.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
