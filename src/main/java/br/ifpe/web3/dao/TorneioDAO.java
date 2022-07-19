package br.ifpe.web3.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Torneio;
import br.ifpe.web3.model.Usuario;

public interface TorneioDAO extends JpaRepository<Torneio, Integer>{

	Torneio findByOrganizador(Usuario usuario);


}
