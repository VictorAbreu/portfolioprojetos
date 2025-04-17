package br.com.victorabreu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.victorabreu.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
