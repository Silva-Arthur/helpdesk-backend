package com.devarthursilva.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.devarthursilva.helpdesk.domain.Cliente;
import com.devarthursilva.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	
	@NotNull(message = "O campo NOME é requerido")
	protected String nome;
	
	@NotNull(message = "O campo CPF é requerido")
	protected String cpf;
	
	@NotNull(message = "O campo E-MAIL é requerido")
	protected String email;
	
	@NotNull(message = "O campo SENHA é requerido")
	protected String senha;
	protected Set<Integer> perfis = new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public ClienteDTO() {
		super();
		addPerfis(Perfil.CLIENTE);
	}

	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.email = cliente.getEmail();
		this.senha = cliente.getSenha();
		this.perfis = cliente.getPerfis().stream().map(p -> p.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = cliente.getDataCriacao();
		addPerfis(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Integer> getPerfis() {
		return perfis;
	}

	public void addPerfis(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
