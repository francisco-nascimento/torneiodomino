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

	private static final Integer PONTOS_VITORIA = 5;
	private static final Integer PONTOS_DERROTA = 0;
	@Autowired
	private PartidaDAO partidaDAO;
	@Autowired
	private TorneioDAO torneioDAO;
	@Autowired
	private DuplaDAO duplaDAO;

	public void salvarTorneio(Torneio torneio) {
		this.torneioDAO.save(torneio);
	}

	public List<Torneio> consultarTorneioPorUsuario(Usuario usuario) {
		return this.torneioDAO.findByOrganizador(usuario);
	}

	public int removerPartidasTorneio(Integer codigo) throws Exception {
		Torneio torneio = this.torneioDAO.getById(codigo);
		if (torneio == null) {
			throw new Exception("Torneio inexistente");
		} else if (!torneio.isPartidasGeradas()) {
			throw new PartidasExistentesTorneioException("Não existem partidas geradas para este torneio!");
		} else if (torneio.isPartidasIniciadas()) {
			throw new Exception("Torneio já foi iniciado. As partidas não podem ser removidas.");
		}
		
		List<Partida> partidas = this.consultarPartidasPorTorneio(codigo);
		this.partidaDAO.deleteAll(partidas);

		torneio.setPartidasGeradas(false);
		this.torneioDAO.save(torneio);
		return partidas.size();
	}
	
	/**
	 * 1. Verificar se o código é de um torneio existente 2. verificar se o número
	 * de duplas é par e tem pelo menos 4 duplas ativas 3. Embaralhar as duplas e
	 * cadatrar as partidas para o torneio 4. Alterar o status do torneio de
	 * partidasGeradas para true
	 * 
	 * @throws Exception
	 */
	public void gerarPartidas(Integer codigo) throws Exception {
		//
		Torneio torneio = this.torneioDAO.getById(codigo);
		if (torneio == null) {
			throw new Exception("Torneio inexistente");
		} else if (torneio.isPartidasGeradas()) {
			throw new PartidasExistentesTorneioException("Partidas já geradas para este torneio");
		}

		List<Dupla> duplas = this.duplaDAO.findAll();
		
		if (duplas != null) {
			if (duplas.size() == 0 || duplas.size() < 4 || duplas.size() % 2 != 0) {
				throw new Exception("Número de duplas precisa ser par e no mínimo 4");
			}
		}

		Collections.shuffle(duplas);
		System.out.println(duplas);

		for (int i = 0; i < duplas.size(); i += 2) {
			Partida partida = new Partida();
			partida.setDupla1(duplas.get(i));
			partida.setDupla2(duplas.get(i + 1));
			partida.setTorneio(torneio);
			partida.setPlacar1(0);
			partida.setPlacar2(0);
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

	public void gerarPartidasPontosCorridos(Integer codigo) throws Exception {
		
		Torneio torneio = this.torneioDAO.getById(codigo);

		if (torneio == null) {
			throw new Exception("Torneio inexistente");
		} else if (torneio.isPartidasGeradas()) {
			throw new PartidasExistentesTorneioException("Partidas já geradas para este torneio");
		}
		
		List<Dupla> duplas = this.duplaDAO.findAll();
		
		if (duplas != null) {
			if (duplas.size() < 4) {
				throw new Exception("Número de duplas precisa ser no mínimo 4");
			}
		}

		Collections.shuffle(duplas);

		for (int i = 0; i < duplas.size(); i++) {
			for (int j = i + 1; j < duplas.size(); j++) {
				
				Partida partida = new Partida();
				partida.setDupla1(duplas.get(i));
				partida.setDupla2(duplas.get(j));
				partida.setTorneio(torneio);
				partida.setPlacar1(0);
				partida.setPlacar2(0);
				partida.setDataPartida(LocalDate.of(2022, Month.AUGUST, 10));
				this.partidaDAO.save(partida);
	
				duplas.get(i).setStatus(StatusDupla.PARTIDA_GERADA);
				this.duplaDAO.save(duplas.get(i));
				duplas.get(j).setStatus(StatusDupla.PARTIDA_GERADA);
				this.duplaDAO.save(duplas.get(i + 1));
			}
		}
		torneio.setPartidasGeradas(true);
		this.torneioDAO.save(torneio);

	}
	
	public void iniciarTorneio(Integer codigo) throws Exception {
		Torneio torneio = this.torneioDAO.getById(codigo);

		if (torneio == null) {
			throw new Exception("Torneio inexistente");
		} else if (!torneio.isPartidasGeradas()) {
			throw new PartidasExistentesTorneioException("Não existem partidas geradas para iniciar o torneio. ");
		} else if (torneio.isPartidasIniciadas()) {
			throw new Exception("Torneio já está iniciado!");
		}
		
		torneio.setPartidasIniciadas(true);
		
		this.torneioDAO.save(torneio);
	}
	
	
	public void registrarPlacar(Integer codigoPartida, Integer placar1, Integer placar2) throws Exception {
		Partida partida = this.partidaDAO.getById(codigoPartida);
		if (partida == null) {
			throw new Exception("Partida inexistente");
		} 
		
		if (placar1 == placar2) {
			throw new Exception("Não pode dar empate");
		} else if (placar1 < 6 && placar2 < 6){
			throw new Exception("Alguma dupla precisa atingir o placar de 6 pontos para finalizar.");
		}
		
		partida.setPlacar1(placar1);
		partida.setPlacar2(placar2);
		
		if (placar1 > placar2) {
			partida.getDupla1().setPontuacao(partida.getDupla1().getPontuacao() + PONTOS_VITORIA);
			partida.getDupla2().setPontuacao(partida.getDupla2().getPontuacao() + PONTOS_DERROTA);
		} else {
			partida.getDupla1().setPontuacao(partida.getDupla1().getPontuacao() + PONTOS_DERROTA);
			partida.getDupla2().setPontuacao(partida.getDupla2().getPontuacao() + PONTOS_VITORIA);			
		}
		
		this.duplaDAO.save(partida.getDupla1());
		this.duplaDAO.save(partida.getDupla2());
		this.partidaDAO.save(partida);
	}

	public void gerarPartidasMataMata(Integer codigo) {

	}

	public List<Partida> consultarPartidasPorTorneio(Integer torneio) {
		return this.partidaDAO.findByTorneioCodigo(torneio);
	}

	public List<Dupla> consultarDuplasClassificacao() {
		return this.duplaDAO.findAll(Sort.by(Sort.Direction.DESC, "pontuacao"));
	}

}
