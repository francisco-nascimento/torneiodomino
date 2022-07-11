package br.ifpe.web3.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.web3.dao.UsuarioDAO;
import br.ifpe.web3.model.Usuario;

@Controller
public class TorneioDominoController {
	private static String USER = "admin";
	private static String PASSWORD = "1234";
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@GetMapping("/admin/home")
	public String exibirHome() {
		return "/home";
	}
	
	@PostMapping("/efetuarLogin")
	public String efetuarLogin(String login, String senha, HttpSession sessao, RedirectAttributes ra) {
		
		Usuario usuario = this.usuarioDAO.findByLoginAndSenha(login, senha);
		if (usuario != null) {
			
			if (usuario.isAtivo()) {
				// Salvar objeto na sessao
				sessao.setAttribute("logado", usuario);
				return "redirect:/admin/home";
				
			} else {
				ra.addFlashAttribute("msg", "Usuário inativo. Favor entrar em contato com o administrador do sistema.");
				return "redirect:/";				
			}
			
		} else {
			ra.addFlashAttribute("msg", "Usuário/senha incorretos");
			return "redirect:/";
		}
	}
	
	@GetMapping("/acesso-negado")
	public String exibirAcessoNegado(Usuario usuario, RedirectAttributes ra) {
		ra.addAttribute("msg", "Acesso Negado! Efetuar login novamente.");
		return "redirect:/";
	}

	@GetMapping("/admin/sair")
	public String logoff(Usuario usuario, HttpSession sessao) {
		sessao.invalidate();
		return "redirect:/";
	}
}
