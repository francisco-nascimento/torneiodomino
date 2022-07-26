package br.ifpe.web3.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Torneio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	private String nome;
	@ManyToOne
	private Usuario organizador;
	private boolean partidasGeradas;
	private boolean partidasIniciadas;
	
	public boolean isPartidasGeradas() {
		return partidasGeradas;
	}
	public void setPartidasGeradas(boolean partidasGeradas) {
		this.partidasGeradas = partidasGeradas;
	}
	public boolean isPartidasIniciadas() {
		return partidasIniciadas;
	}
	public void setPartidasIniciadas(boolean partidasIniciadas) {
		this.partidasIniciadas = partidasIniciadas;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Usuario getOrganizador() {
		return organizador;
	}
	public void setOrganizador(Usuario organizador) {
		this.organizador = organizador;
	}
	
}
