package br.ifpe.web3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ifpe.web3.dao.UsuarioDAO;
import br.ifpe.web3.model.Usuario;
import br.ifpe.web3.util.EmailService;

import java.util.List;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private EmailService emailService;

	public void solicitarCadastro(Usuario usuario) throws Exception {

		// verificar se existe usuario com mesmo login e email
		Usuario user = this.usuarioDAO.findByLogin(usuario.getLogin());
		if (user != null) {
			throw new Exception("Login já utilizado por outro usuário");
		}

		user = this.usuarioDAO.findByEmail(usuario.getEmail());
		if (user != null) {
			throw new Exception("Já existe usuário com este e-mail");
		}
		this.usuarioDAO.save(usuario);
	}

	public void salvarAlteracoes(Usuario usuario) throws Exception {

		// verificar se existe usuario com mesmo login e email
		Usuario user = this.usuarioDAO.findByLogin(usuario.getLogin());
		if (user != null && user.getCodigo() != usuario.getCodigo()) {
			throw new Exception("Login já utilizado por outro usuário");
		}

		user = this.usuarioDAO.findByEmail(usuario.getEmail());
		if (user != null && user.getCodigo() != usuario.getCodigo()) {
			throw new Exception("Já existe usuário com este e-mail");
		}
		usuario.setSenha(user.getSenha());
		this.usuarioDAO.save(usuario);
	}

	public List<Usuario> listarUsuarios(String ordem) {
		return this.usuarioDAO.findAll(Sort.by(ordem));
	}

	public Usuario consultarUsuarioPeloId(Integer id) {
		return this.usuarioDAO.getById(id);
	}

	public void removerUsuario(Integer codigo) {
		this.usuarioDAO.deleteById(codigo);

	}
	
	public void enviarEmailEsqueceuSenha(String login) throws Exception {
		Usuario usuario = this.usuarioDAO.findByLogin(login);
		if (usuario != null) {
			this.emailService.enviarEmailEsqueceuSenha(usuario.getEmail(), usuario.getSenha());
		} else {
			throw new Exception("Usuário inexistente");
		}
	}
}
