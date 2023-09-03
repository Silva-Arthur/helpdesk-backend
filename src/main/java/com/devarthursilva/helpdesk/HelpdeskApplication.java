package com.devarthursilva.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devarthursilva.helpdesk.domain.Chamado;
import com.devarthursilva.helpdesk.domain.Cliente;
import com.devarthursilva.helpdesk.domain.Tecnico;
import com.devarthursilva.helpdesk.domain.enums.Perfil;
import com.devarthursilva.helpdesk.domain.enums.Prioridade;
import com.devarthursilva.helpdesk.domain.enums.Status;
import com.devarthursilva.helpdesk.repositories.ChamadoRepository;
import com.devarthursilva.helpdesk.repositories.ClienteRepository;
import com.devarthursilva.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Arthur Silva", "26490331004", "arthur@email.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Beatriz Pinho", "49125740075", "beatriz@email.com", "123");
		
		Chamado cha1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(cha1));
	}

}
