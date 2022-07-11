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
	private Integer pontuacaoDupla1;
	private Integer pontuacaoDupla2;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPartida;
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
	public Integer getPontuacaoDupla1() {
		return pontuacaoDupla1;
	}
	public void setPontuacaoDupla1(Integer pontuacaoDupla1) {
		this.pontuacaoDupla1 = pontuacaoDupla1;
	}
	public Integer getPontuacaoDupla2() {
		return pontuacaoDupla2;
	}
	public void setPontuacaoDupla2(Integer pontuacaoDupla2) {
		this.pontuacaoDupla2 = pontuacaoDupla2;
	}
	public LocalDate getDataPartida() {
		return dataPartida;
	}
	public void setDataPartida(LocalDate dataPartida) {
		this.dataPartida = dataPartida;
	}
	
	
	
}
