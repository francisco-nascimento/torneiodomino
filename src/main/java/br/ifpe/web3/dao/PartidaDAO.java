package br.ifpe.web3.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Partida;
import br.ifpe.web3.model.Torneio;

public interface PartidaDAO extends JpaRepository<Partida, Integer>{

	List<Partida> findByTorneio(Torneio torneio);

}
