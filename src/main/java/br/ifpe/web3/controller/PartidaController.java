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
import br.ifpe.web3.services.PartidasExistentesTorneioException;

@Controller
public class PartidaController {

	@Autowired
	private PartidaService partidaService;

	@GetMapping("/admin/partidas")
	public String exibirPartidas(Model model, HttpSession session) {

//		Usuario logado = this.obterUsuarioLogado(session);
//		if (logado != null) {
//			torneio = this.partidaService.consultarTorneioPorUsuario(logado);
//		}
		Torneio torneio = (Torneio) session.getAttribute("torneioAtivo");
		if (torneio == null) {
			torneio = new Torneio();
		} else {
			List<Partida> partidas = this.partidaService.consultarPartidasPorTorneio(torneio.getCodigo());
			model.addAttribute("listaPartidas", partidas);
			List<Dupla> duplas = this.partidaService.consultarDuplasClassificacao();
			model.addAttribute("classificacao", duplas);
		}
		model.addAttribute("torneio", torneio);
		return "partida/torneio-partidas";
	}


	@GetMapping("/admin/gerarPartidas")
	public String gerarPartidas(@RequestParam Integer codigo, RedirectAttributes ra) {
		try {
			this.partidaService.gerarPartidas(codigo);
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/partidas";
	}

	@GetMapping("/admin/zerarPartidas")
	public String zerarPartidas(@RequestParam Integer codigo, RedirectAttributes ra) {
		try {
			int qtd = this.partidaService.removerPartidasTorneio(codigo);
			ra.addFlashAttribute("msg", "Foram removidas " + qtd + " partidas.");

		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("msg", e.getMessage());
		}

		return "redirect:/admin/partidas";

	}
	
	@PostMapping("/admin/registrarPlacar")
	public String registrarPlacar(Integer codigo, Integer placar1, Integer placar2, RedirectAttributes ra) {
		System.out.println(codigo + " - " + placar1 + " x " + placar2);
		try {
			this.partidaService.registrarPlacar(codigo, placar1, placar2);
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("msg2", e.getMessage());
		}
		return "redirect:/admin/partidas";
	}

	@GetMapping("/admin/iniciarTorneio")
	public String iniciarTorneio(Integer codigo, RedirectAttributes ra) {
		try {
			this.partidaService.iniciarTorneio(codigo);
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/admin/partidas";
	}
	

}
