package com.unicred.entity;

public class ResultRest {
	
	public static final String STATUS_CODE_ERRO_SQL = "SQL_ERROR";	

	public static final String STATUS_OK = "OK";
	
	public static final String STATUS_ERROR = "ERROR" ;
	
	public static final String STATUS_VOTO = "UNABLE_TO_VOTE" ;
	
	public static final String TIME_ERROR = "TEMPO_ESGOTADO" ;
	
	public static final String PARAMETER_ERROR = "PARAMETER_ERROR" ;
	
	private String status;
	private String error = "";

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

		
}

