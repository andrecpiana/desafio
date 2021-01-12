package com.unicred.entity;

public class ReportPauta {
	
	PautaParameter pauta;
	int totalVotos;
	int votosSim;
	int votosNao;
	
	public int getTotalVotos() {
		return totalVotos;
	}
	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}
	public PautaParameter getPauta() {
		return pauta;
	}
	public void setPauta(PautaParameter pauta) {
		this.pauta = pauta;
	}
	public int getVotosSim() {
		return votosSim;
	}
	public void setVotosSim(int votosSim) {
		this.votosSim = votosSim;
	}
	public int getVotosNao() {
		return votosNao;
	}
	public void setVotosNao(int votosNao) {
		this.votosNao = votosNao;
	}
	
	
}
