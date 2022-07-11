package br.ifpe.web3.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpe.web3.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer>{


	boolean existsByLoginAndSenha(String login, String senha);
	
	Usuario findByLogin(String login);
	
	Usuario findByEmail(String email);

	Usuario findByLoginAndSenha(String login, String senha);
}
