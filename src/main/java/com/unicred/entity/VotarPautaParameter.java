package com.unicred.entity;

public class VotarPautaParameter {
	
	int codigo;
	int pauta;
	long associado;
	String voto;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getPauta() {
		return pauta;
	}
	public void setPauta(int pauta) {
		this.pauta = pauta;
	}
	
	public String getVoto() {
		return voto;
	}
	public void setVoto(String voto) {
		this.voto = voto;
	}
	public long getAssociado() {
		return associado;
	}
	public void setAssociado(long associado) {
		this.associado = associado;
	}
	
	
}
