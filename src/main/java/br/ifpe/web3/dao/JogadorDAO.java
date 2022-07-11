package br.ifpe.web3.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ifpe.web3.model.Categoria;
import br.ifpe.web3.model.Jogador;

public interface JogadorDAO extends JpaRepository<Jogador, Integer>{

	List<Jogador> findByNomeContainingIgnoreCase(String nome, Sort sort);

	List<Jogador> findByNomeContainingIgnoreCaseAndCategoria(String nome, Categoria categoria, Sort sort);
	
	@Query("Select j from Jogador j where upper(j.nome) like upper(concat('%', :nome, '%')) and j.categoria = :categoria")
	List<Jogador> consultarJogadoresPorNomeCategoria(
			String nome, Categoria categoria);
	
	@Query("select j from Jogador j where j not in (select d.jogador1 from Dupla d) "
			+ " and j not in (select d.jogador2 from Dupla d) order by j.nome")
	List<Jogador> consultarJogadoresSemDupla();

}
