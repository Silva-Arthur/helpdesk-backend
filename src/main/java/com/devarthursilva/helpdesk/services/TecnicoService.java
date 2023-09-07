package com.devarthursilva.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devarthursilva.helpdesk.domain.Pessoa;
import com.devarthursilva.helpdesk.domain.Tecnico;
import com.devarthursilva.helpdesk.domain.dtos.TecnicoDTO;
import com.devarthursilva.helpdesk.repositories.PessoaRepository;
import com.devarthursilva.helpdesk.repositories.TecnicoRepository;
import com.devarthursilva.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.devarthursilva.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		validaProCpfEEmail(tecnicoDTO);
		
		Tecnico tecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(tecnico);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(id);
		Tecnico oldTecnico = findById(id);
		
		if (!tecnicoDTO.getSenha().equals(oldTecnico.getSenha())) {
			tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		}
		
		validaProCpfEEmail(tecnicoDTO);
		oldTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(oldTecnico);
	}
	

	public void delete(Integer id) {
		Tecnico tecnico = findById(id);
		
		if (tecnico.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		} 
		
		tecnicoRepository.deleteById(id);
	}
	
	private void validaProCpfEEmail(TecnicoDTO tecnicoDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
		
		if (obj.isPresent() && obj.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
		
		if (obj.isPresent() && obj.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

}
