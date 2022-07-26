package br.ifpe.web3.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Torneio;
import br.ifpe.web3.model.Usuario;

public interface TorneioDAO extends JpaRepository<Torneio, Integer>{

	List<Torneio> findByOrganizador(Usuario usuario);
	
	Torneio findByCodigo(Integer codigo);


}
