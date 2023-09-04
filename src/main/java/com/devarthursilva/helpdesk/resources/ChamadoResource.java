package com.devarthursilva.helpdesk.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devarthursilva.helpdesk.domain.Chamado;
import com.devarthursilva.helpdesk.domain.dtos.ChamadoDTO;
import com.devarthursilva.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {

	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(
			@PathVariable Integer id) {
		Chamado obj = chamadoService.findById(id);
		
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}
	
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {
		List<Chamado> list = chamadoService.findAll();
		
		List<ChamadoDTO> listDTO = list.stream().map(cha -> new ChamadoDTO(cha)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
}
