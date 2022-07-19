package br.ifpe.web3.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ifpe.web3.dao.DuplaDAO;
import br.ifpe.web3.dao.PartidaDAO;
import br.ifpe.web3.dao.TorneioDAO;
import br.ifpe.web3.model.Dupla;
import br.ifpe.web3.model.Partida;
import br.ifpe.web3.model.StatusDupla;
import br.ifpe.web3.model.Torneio;
import br.ifpe.web3.model.Usuario;

@Service
public class PartidaService {

	@Autowired
	private PartidaDAO partidaDAO;
	@Autowired
	private TorneioDAO torneioDAO;
	@Autowired
	private DuplaDAO duplaDAO;
	
	
	public void salvarTorneio(Torneio torneio) {
		this.torneioDAO.save(torneio);
	}
	
	public Torneio consultarTorneioPorUsuario(Usuario usuario) {
		return this.torneioDAO.findByOrganizador(usuario);
	}
	/**
	 * 1. Verificar se o código é de um torneio existente
	 * 2. verificar se o número de duplas é par e tem pelo menos 4 duplas ativas
	 * 3. Embaralhar as duplas e cadatrar as partidas para o torneio 
	 * 4. Alterar o status do torneio de partidasGeradas para true
	 * @throws Exception 
	 */
	public void gerarPartidas(Integer codigo) throws Exception {
		//  
		Torneio torneio = this.torneioDAO.getById(codigo);
		if (torneio == null) {
			throw new Exception("Torneio inexistente");
		}
		
		List<Dupla> duplas = this.duplaDAO.findAll();
		if (duplas != null) {
			if (duplas.size() == 0 
				|| duplas.size() < 4 
				|| duplas.size() % 2 != 0) {
				throw new Exception("Número de duplas precisa ser par e no mínimo 4");
			}
		}
		
		Collections.shuffle(duplas);
		System.out.println(duplas);
		
		for(int i= 0; i < duplas.size(); i+=2) {
			Partida partida = new Partida();
			partida.setDupla1(duplas.get(i));
			partida.setDupla2(duplas.get(i + 1));
			partida.setTorneio(torneio);
			partida.setPontuacaoDupla1(0);
			partida.setPontuacaoDupla2(0);
			partida.setDataPartida(LocalDate.of(2022, Month.AUGUST, 10));
			this.partidaDAO.save(partida);
			
			duplas.get(i).setStatus(StatusDupla.PARTIDA_GERADA);
			this.duplaDAO.save(duplas.get(i));
			duplas.get(i + 1).setStatus(StatusDupla.PARTIDA_GERADA);
			this.duplaDAO.save(duplas.get(i + 1));
		}
		
		torneio.setPartidasGeradas(true);
		this.torneioDAO.save(torneio);
		
	}

	public List<Partida> consultarPartidasPorTorneio(Torneio torneio) {
		return this.partidaDAO.findByTorneio(torneio);
	}

	public List<Dupla> consultarDuplasClassificacao() {
		return this.duplaDAO.findAll(Sort.by(Sort.Direction.DESC, "pontuacao"));
	}	
	
	
}
