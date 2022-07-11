package br.ifpe.web3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ifpe.web3.dao.JogadorDAO;
import br.ifpe.web3.model.Jogador;

@Controller
@RequestMapping("/admin")
public class JogadorController {

	@Autowired
	private JogadorDAO jogadorDAO;

	@GetMapping("/cadJogador")
	public String cadastrarJogador(Jogador jogador) {
		return "jogador/cadastrar";
	}

	@GetMapping("/findJogador")
	public String listarJogadores(Jogador jogador, @RequestParam(defaultValue = "nome") String ordem, Model model) {
		if (jogador.getCategoria() == null && jogador.getNome() == null) {
			model.addAttribute("colecao", this.jogadorDAO.findAll(Sort.by(ordem)));
		} else {
			if (jogador.getCategoria() == null) {
				model.addAttribute("colecao",
						this.jogadorDAO.findByNomeContainingIgnoreCase(jogador.getNome(), Sort.by(ordem)));
			} else {
				model.addAttribute("colecao", this.jogadorDAO.findByNomeContainingIgnoreCaseAndCategoria(
						jogador.getNome(), jogador.getCategoria(), Sort.by(ordem)));
			}

		}
		return "/jogador/pesquisar";
	}

	@PostMapping("/salvarJogador")
	public String salvarJogadores(Jogador jogador, Model model) {
		this.jogadorDAO.save(jogador);
		return "redirect:/admin/findJogador";
	}

	@GetMapping("/editarJogador")
	public String editarJogador(Integer codigo, Model model) {
		Jogador jogador = this.jogadorDAO.getById(codigo);
		model.addAttribute("jogador", jogador);
		return "/jogador/cadastrar";
	}

	@GetMapping("/removerJogador")
	public String removerJogador(Integer codigo) {
		this.jogadorDAO.deleteById(codigo);
		return "redirect:/admin/findJogador";

	}
};