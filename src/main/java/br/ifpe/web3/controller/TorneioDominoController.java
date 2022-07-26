package br.ifpe.web3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.web3.dao.TorneioDAO;
import br.ifpe.web3.dao.UsuarioDAO;
import br.ifpe.web3.model.Torneio;
import br.ifpe.web3.model.Usuario;
import br.ifpe.web3.services.PartidaService;

@Controller
public class TorneioDominoController {

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private TorneioDAO torneioDAO;
	@Autowired
	private PartidaService partidaService;

	@GetMapping("/admin/home")
	public String exibirHome(Torneio torneio, Model model, HttpSession sessao) {

		Usuario user = (Usuario) sessao.getAttribute("logado");

		List<Torneio> torneios = this.torneioDAO.findByOrganizador(user);
		model.addAttribute("torneios", torneios);
		return "home";
	}

	@GetMapping("/admin/selecionarTorneio")
	public String selecionarTorneio(Integer codigo, HttpSession sessao) {

		Torneio torneio = this.torneioDAO.findByCodigo(codigo);
		sessao.setAttribute("torneioAtivo", torneio);
		return "redirect:/admin/home";
	}

	@PostMapping("/admin/gerarTorneio")
	public String gerarTorneio(Torneio torneio, HttpSession session) {
		Usuario logado = this.obterUsuarioLogado(session);
		torneio.setOrganizador(logado);
		this.partidaService.salvarTorneio(torneio);
		return "redirect:/admin/home";
	}

	@PostMapping("/efetuarLogin")
	public String efetuarLogin(Usuario usuario, HttpSession sessao, RedirectAttributes ra) {

		Usuario user = this.usuarioDAO.findByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
		if (user != null) {

			if (user.isAtivo()) {
				// Salvar objeto na sessao
				sessao.setAttribute("logado", user);
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

	private Usuario obterUsuarioLogado(HttpSession session) {
		if (session != null && session.getAttribute("logado") != null) {
			return (Usuario) session.getAttribute("logado");
		} else {
			return null;
		}
	}
}
