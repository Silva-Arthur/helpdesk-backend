package com.devarthursilva.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devarthursilva.helpdesk.domain.Cliente;
import com.devarthursilva.helpdesk.domain.dtos.ClienteDTO;
import com.devarthursilva.helpdesk.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente obj = clienteService.findById(id);
		
		return ResponseEntity.ok(new ClienteDTO(obj));
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteService.findAll();
		List<ClienteDTO> listDTO = list.stream().map(t -> new ClienteDTO(t)).collect(Collectors.toList());
	
		return ResponseEntity.ok(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> create(
			@Valid
			@RequestBody 
			ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.create(clienteDTO);
		// Redireciona para a URI de findById, para mostrar o técnico criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		
		//Retorno 201 [created] - para novo obj criado
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(
			@PathVariable Integer id,
			@Valid @RequestBody	ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.update(id, clienteDTO);
		
		return ResponseEntity.ok().body(new ClienteDTO(cliente));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(
			@PathVariable Integer id){
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
