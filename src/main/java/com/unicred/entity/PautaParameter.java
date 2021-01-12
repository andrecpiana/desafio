package com.unicred.entity;

import java.util.ArrayList;
import java.util.List;

public class PautaParameter {
	
	int codigo;
	String titulo;
	String descricao;
	String data;
	
	List<VotarPautaParameter> votarPauta = new ArrayList<VotarPautaParameter>();
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
		
}
