package br.ifpe.web3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PartidaController {
	
		//@Autowired
		//private PartidaDAO partidaDAO;
	
	@GetMapping("/partidas")
	public String exibirPartidas() {
		return "/partida/dash-partidas";
	}
}
