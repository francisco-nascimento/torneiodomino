package br.ifpe.web3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

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
	@Type(type = "yes_no")
	@Column(length = 1)
	boolean eliminada;
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
	public boolean isEliminada() {
		return eliminada;
	}
	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}
	
	
	
	
}
