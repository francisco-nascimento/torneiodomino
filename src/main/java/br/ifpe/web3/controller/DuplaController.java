package br.ifpe.web3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifpe.web3.dao.DuplaDAO;
import br.ifpe.web3.dao.JogadorDAO;
import br.ifpe.web3.model.Dupla;
import br.ifpe.web3.model.StatusDupla;

@Controller
@RequestMapping("/admin")
public class DuplaController {

	@Autowired
	private DuplaDAO duplaDAO;
	
	@Autowired
	private JogadorDAO jogadorDAO;

	@GetMapping("/cadDupla")
	public String CadastrarDupla(Dupla dupla, Model model) {
		model.addAttribute("listaJogadores", this.jogadorDAO.consultarJogadoresSemDupla());
		return "dupla/cadastrar-dupla";
	}

	@GetMapping("/findDupla")
	public String listarDuplas(Model model) {
		model.addAttribute("colecao", this.duplaDAO.findAll(Sort.by("pontuacao")));
		return "dupla/listar-dupla";
	}

	@PostMapping("/salvarDupla")
	public String salvarJogadores(Dupla dupla, Model model) {
		if (dupla.getJogador1() == null || dupla.getJogador2() == null) {
			model.addAttribute("msg", "Selecione dois jogadores para formar a dupla");
			return this.CadastrarDupla(dupla, model);
		}
		if (dupla.getJogador1().getCodigo() == dupla.getJogador2().getCodigo()) {
			model.addAttribute("msg", "Selecione jogadores diferentes");
			return this.CadastrarDupla(dupla, model);
		}
		if (duplaDAO.existsByJogador1AndJogador2(dupla.getJogador1(), dupla.getJogador2()) ||
			duplaDAO.existsByJogador1AndJogador2(dupla.getJogador2(), dupla.getJogador1())
			) {
			model.addAttribute("msg", "Já existe dupla cadastrada com esses jogadores");
			return this.CadastrarDupla(dupla, model);
		}
		dupla.setPontuacao(0);
		dupla.setStatus(StatusDupla.SEM_PARTIDA);
		this.duplaDAO.save(dupla);
		return "redirect:/admin/findDupla";
	}

	@GetMapping("/removerDupla")
	public String removerDupla() {
		return "dupla/remover-dupla";

	}

	@PostMapping("/removerDupla")
	public String excluirDupla(Integer numeroInscricao, Model model) {
		this.duplaDAO.deleteById(numeroInscricao);
		model.addAttribute("msg", "Dupla excluída com sucesso");
		return "dupla/remover-dupla";
	}

}
