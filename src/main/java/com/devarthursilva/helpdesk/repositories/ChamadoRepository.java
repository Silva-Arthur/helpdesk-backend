package com.devarthursilva.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devarthursilva.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
