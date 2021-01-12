package com.unicred.service;

import java.sql.SQLException;

import com.unicred.dao.PautaDao;
import com.unicred.entity.PautaParameter;
import com.unicred.entity.ReportPauta;
import com.unicred.entity.ResultRest;
import com.unicred.entity.VotarPautaParameter;
import com.unicred.rest.SaidaService;

public class PautaService {
	
	public ResultRest inserePauta(PautaParameter pautaParameter){
		ResultRest resultRest = new ResultRest();	
		try {
			PautaDao dao = new PautaDao();
			dao.inserePauta(pautaParameter);
			resultRest.setStatus( ResultRest.STATUS_OK );
			return resultRest;
		} catch(SQLException e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_CODE_ERRO_SQL);
			resultRest.setError(e.getMessage());
			return resultRest;
		} catch(Exception e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_ERROR );
			return resultRest;
		}			
	}
	
	public ResultRest abrePauta(PautaParameter pautaParameter){
		ResultRest resultRest = new ResultRest();	
				
		try {
			PautaDao dao = new PautaDao();
			dao.abrePauta(pautaParameter);			
			resultRest.setStatus( ResultRest.STATUS_OK );
			return resultRest;
		} catch(SQLException e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_CODE_ERRO_SQL );
			resultRest.setError(e.getMessage());
			return resultRest;
		} catch(Exception e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_ERROR );
			return resultRest;
		}	
		
	}
	
	public ResultRest votaPauta(VotarPautaParameter votarPautaParameter){
		ResultRest resultRest = new ResultRest();	
		try {
			PautaDao dao = new PautaDao();
			dao.votaPauta(votarPautaParameter);
			resultRest.setStatus( ResultRest.STATUS_OK );		
			return resultRest;
		} catch(SQLException e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_CODE_ERRO_SQL );
			resultRest.setError(e.getMessage());
			return resultRest;
		} catch(Exception e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_ERROR );
			return resultRest;
		}	
		
	}	
	
	public ResultRest validaPauta(PautaParameter pautaParameter, VotarPautaParameter votarPautaParameter){
		ResultRest resultRest = new ResultRest();
		//Tarefa Principal - validar parametros para 
		System.out.println("Tarefa Principal - padronizar");
		if (!votarPautaParameter.getVoto().contains("SIM") && !votarPautaParameter.getVoto().contains("NAO")) {
			resultRest.setStatus( ResultRest.PARAMETER_ERROR );	
			resultRest.setError( "SIM / NAO");	
			return resultRest;	
		}
			
		//Tarefa Principal - validar tempo
		System.out.println("Tarefa Principal - validar tempo");
		try {
			PautaDao dao = new PautaDao();
			if (dao.validaTempo(pautaParameter) == false) {		
				resultRest.setStatus( ResultRest.TIME_ERROR );	
				return resultRest;
			}
			
		} catch(SQLException e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_CODE_ERRO_SQL );
			resultRest.setError(e.getMessage());
			return resultRest;
		} catch(Exception e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_ERROR );
			return resultRest;
		}
		
		// Tarefa Bônus 1 - Integração com sistemas externos
		System.out.println("Tarefa Bônus 1 - Integração com sistemas externos");
		try {
			
			SaidaService saida = new SaidaService();
			String validaCPF = saida.sendGet("https://user-info.herokuapp.com/users/",String.valueOf(votarPautaParameter.getAssociado()));
			
			if (validaCPF.contains("UNABLE_TO_VOTE")){						
				ResultRest resultValida = new ResultRest();
				resultValida.setStatus(ResultRest.STATUS_VOTO);
				return resultValida;				
			}
		} catch(Exception e) {
			System.out.println(e);
			resultRest.setStatus( ResultRest.STATUS_VOTO);
			resultRest.setError(e.getMessage());
			return resultRest;
		}
	
		resultRest.setStatus( ResultRest.STATUS_OK );		
		return resultRest;
	}
	
	public ReportPauta reportPauta(int codigo){
		ReportPauta reportRest = new ReportPauta();	
		try {			
			//Tarefa Principal - buscar resultdo
			PautaDao dao = new PautaDao();
			reportRest = dao.buscaRelatorio(codigo);
			return reportRest;
		} catch(Exception e) {
			throw new UnsupportedOperationException(ResultRest.STATUS_ERROR + e);
		}
		
		
	}
}
