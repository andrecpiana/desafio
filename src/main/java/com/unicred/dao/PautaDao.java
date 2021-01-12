package com.unicred.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.unicred.dao.SimpleJdbc.Configuracao;
import com.unicred.entity.PautaParameter;
import com.unicred.entity.ReportPauta;
import com.unicred.entity.VotarPautaParameter;

public class PautaDao {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	CallableStatement cstmt = null; 
	public ResultSet rs = null;	
	
	public void inserePauta(PautaParameter pautaParameter) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into PAUTA (codigo, titulo, descricao, data_inicio) values (? ,? ,? , null)");
				
		try {
			con = Configuracao.getInstancia().getConexao();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, pautaParameter.getCodigo());		
			pstmt.setString(2, pautaParameter.getTitulo());	
			pstmt.setString(3, pautaParameter.getDescricao());	
			pstmt.executeUpdate();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
		}	
	}
	
	public void abrePauta(PautaParameter pautaParameter) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("update PAUTA set data_inicio = current_timestamp, aberto = 1 where codigo = ?");
				
		try {
			con = Configuracao.getInstancia().getConexao();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, pautaParameter.getCodigo());		
			pstmt.executeUpdate();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
		}	
	}
	
	public void votaPauta(VotarPautaParameter parameter) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into voto_pauta ( pauta, associado, voto) values (?, ?, ?)");
				
		try {
			con = Configuracao.getInstancia().getConexao();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, parameter.getPauta());
			pstmt.setLong(2, parameter.getAssociado());
			pstmt.setString(3, parameter.getVoto());
			pstmt.executeUpdate();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {}
		}	
	}
	
	public boolean validaTempo(PautaParameter pautaParameter) throws Exception  {

	
		StringBuilder sql = new StringBuilder();
		sql.append("select 1 from pauta where current_timestamp <= data_inicio + (1 * interval '1 minute') and aberto = 1 and codigo = ?");
			
		try {
			con = Configuracao.getInstancia().getConexao();
			pstmt =  con.prepareStatement(sql.toString());
			pstmt.setInt(1,  pautaParameter.getCodigo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;				
			}
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {}
		}
		System.out.println("return true validaTempo");
		return false;
	}
	
	public ReportPauta buscaRelatorio(int codigo) throws Exception  {

		StringBuilder sql = new StringBuilder();
		sql.append("select pauta.*, "
				+ " (select coalesce(sum(1),0) from voto_pauta where pauta = pauta.codigo) as total, "
				+ " (select coalesce(sum(1),0) from voto_pauta where pauta = pauta.codigo and voto = 'SIM') as totalSim, "
				+ " (select coalesce(sum(1),0) from voto_pauta where pauta = pauta.codigo and voto = 'NAO') as totalNao "
				+ "from pauta where codigo = ?");
		ReportPauta report = new ReportPauta();	
		try {
			con = Configuracao.getInstancia().getConexao();
			pstmt =  con.prepareStatement(sql.toString());
			pstmt.setInt(1,   codigo);
			rs = pstmt.executeQuery();
			if (rs.next()) {	
				PautaParameter p = new PautaParameter();
				
				p.setCodigo(rs.getInt("codigo"));
				p.setTitulo(rs.getString("titulo"));
				p.setDescricao(rs.getString("descricao"));
				report.setPauta(p);
				report.setTotalVotos(rs.getInt("total"));
				report.setVotosSim(rs.getInt("totalSim"));
				report.setVotosNao(rs.getInt("totalNao"));
				return report;				
			}
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {}
		}
		return report;		
	}
}
