package br.ifpe.web3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.web3.model.Dupla;
import br.ifpe.web3.model.Partida;
import br.ifpe.web3.model.Torneio;
import br.ifpe.web3.model.Usuario;
import br.ifpe.web3.services.PartidaService;

@Controller
public class PartidaController {
	
		@Autowired
		private PartidaService  partidaService;
	
	@GetMapping("/admin/partidas")
	public String exibirPartidas(Torneio torneio, Model model, HttpSession session) {
		
		Usuario logado = this.obterUsuarioLogado(session);
		if (logado != null) {
			torneio = this.partidaService.consultarTorneioPorUsuario(logado);
		}
		if (torneio == null) {
			torneio = new Torneio();
		} else {
			List<Partida> partidas = this.partidaService.consultarPartidasPorTorneio(torneio);
			model.addAttribute("listaPartidas", partidas);
			List<Dupla> duplas = this.partidaService.consultarDuplasClassificacao();
			model.addAttribute("classificacao", duplas);
		}
		model.addAttribute("torneio", torneio);
		return "/partida/torneio-partidas";
	}
	
	@PostMapping("/admin/gerarTorneio")
	public String gerarTorneio(Torneio torneio, HttpSession session) {
		Usuario logado = this.obterUsuarioLogado(session);
		torneio.setOrganizador(logado);
		this.partidaService.salvarTorneio(torneio);
		return "redirect:/admin/partidas"; 
	}
	
	@GetMapping("/admin/gerarPartidas")
	public String gerarPartidas(@RequestParam Integer codigo, RedirectAttributes ra) {
		try {
			this.partidaService.gerarPartidas(codigo);
		} catch (Exception e) {
			ra.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/partidas"; 
	}
	
	private Usuario obterUsuarioLogado(HttpSession session) {
		if (session != null && session.getAttribute("logado") != null) {
			return (Usuario) session.getAttribute("logado");
		} else {
			return null;
		}
	}
}
