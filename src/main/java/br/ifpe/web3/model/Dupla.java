package br.ifpe.web3.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Dupla {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer numeroInscricao;
	@ManyToOne
	private Jogador jogador1;
	@ManyToOne
	private Jogador jogador2;
	//boolean taxaInscricao;
	private Integer pontuacao;
	@Enumerated(EnumType.STRING)
	private StatusDupla status;
	
	public StatusDupla getStatus() {
		return status;
	}
	public void setStatus(StatusDupla status) {
		this.status = status;
	}
	public Integer getNumeroInscricao() {
		return numeroInscricao;
	}
	public void setNumeroInscricao(Integer numeroInscricao) {
		this.numeroInscricao = numeroInscricao;
	}
	public Jogador getJogador1() {
		return jogador1;
	}
	public void setJogador1(Jogador jogador1) {
		this.jogador1 = jogador1;
	}
	public Jogador getJogador2() {
		return jogador2;
	}
	public void setJogador2(Jogador jogador2) {
		this.jogador2 = jogador2;
	}
	public Integer getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}
	@Override
	public String toString() {
		return "Dupla [numeroInscricao=" + numeroInscricao + ", status=" + status + "]";
	}
	
	
	
	
}
