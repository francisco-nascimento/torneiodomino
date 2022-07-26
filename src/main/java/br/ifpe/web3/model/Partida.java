package br.ifpe.web3.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Partida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	@ManyToOne
	private Dupla dupla1;
	@ManyToOne
	private Dupla dupla2;
	private Integer placar1;
	private Integer placar2;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPartida;
	@ManyToOne
	private Torneio torneio;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Torneio getTorneio() {
		return torneio;
	}
	public void setTorneio(Torneio torneio) {
		this.torneio = torneio;
	}
	public Dupla getDupla1() {
		return dupla1;
	}
	public void setDupla1(Dupla dupla1) {
		this.dupla1 = dupla1;
	}
	public Dupla getDupla2() {
		return dupla2;
	}
	public void setDupla2(Dupla dupla2) {
		this.dupla2 = dupla2;
	}

	public LocalDate getDataPartida() {
		return dataPartida;
	}
	public void setDataPartida(LocalDate dataPartida) {
		this.dataPartida = dataPartida;
	}
	public Integer getPlacar1() {
		return placar1;
	}
	public void setPlacar1(Integer placar1) {
		this.placar1 = placar1;
	}
	public Integer getPlacar2() {
		return placar2;
	}
	public void setPlacar2(Integer placar2) {
		this.placar2 = placar2;
	}
	
}
