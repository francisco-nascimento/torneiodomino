package br.ifpe.web3.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Dupla;
import br.ifpe.web3.model.Jogador;

public interface DuplaDAO extends JpaRepository<Dupla, Integer>{

	boolean existsByJogador1AndJogador2(Jogador jogador1, Jogador jogador2);

}
