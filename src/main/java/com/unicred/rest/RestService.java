package com.unicred.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.unicred.entity.PautaParameter;
import com.unicred.entity.ReportPauta;
import com.unicred.entity.ResultRest;
import com.unicred.entity.VotarPautaParameter;
import com.unicred.service.PautaService;


@Path("/")
public class RestService {
	
	// Cadastrar uma nova pauta
	@POST
	@Path("/pauta/criar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarPauta(PautaParameter pautaParameter){		

		PautaService pautaService = new PautaService();		
		
		ResultRest resultService =  pautaService.inserePauta(pautaParameter);
		
		if (resultService.getStatus().equals(ResultRest.STATUS_OK)){
			return Response.status(200).entity( resultService  ).build(); 
		} else if (resultService.getStatus().equals(ResultRest.STATUS_CODE_ERRO_SQL)){
			return Response.status(200).entity( resultService  ).build(); 
		} else {
			return Response.status(400).build(); 	
		}
	
	}
	// Abrir uma sessão de votação em uma pauta
	@POST
	@Path("/pauta/abrir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response abrirPauta(PautaParameter pautaParameter){		
		
		PautaService pautaService = new PautaService();		
		ResultRest resultService = pautaService.abrePauta(pautaParameter);
		
		if (resultService.getStatus().equals(ResultRest.STATUS_OK)){
			return Response.status(200).entity( resultService  ).build(); 
		} else if (resultService.getStatus().equals(ResultRest.STATUS_CODE_ERRO_SQL)){
			return Response.status(200).entity( resultService  ).build(); 
		} else {
			return Response.status(400).build(); 	
		}
	}
    // Receber votos dos associados em pautas 
	@POST
	@Path("/votar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response votarPauta(VotarPautaParameter votarPautaParameter){
		
		PautaService pautaService = new PautaService();		
		PautaParameter pauta = new PautaParameter();
		pauta.setCodigo(votarPautaParameter.getPauta());	
		// Validacao da pauta
		
		ResultRest resultService = pautaService.validaPauta(pauta,votarPautaParameter);
		// Se ok executa a votacao
		if (resultService.getStatus().equals(ResultRest.STATUS_OK)){
			
			resultService = pautaService.votaPauta(votarPautaParameter);
			
			if (resultService.getStatus().equals(ResultRest.STATUS_OK)){
				return Response.status(200).build(); 
			} else if (resultService.getStatus().equals(ResultRest.STATUS_CODE_ERRO_SQL)){
				return Response.status(200).entity( resultService  ).build(); 
			} else {
				return Response.status(400).build(); 	
			}
		} else if (resultService.getStatus().equals(ResultRest.STATUS_CODE_ERRO_SQL)){
			return Response.status(200).entity( resultService  ).build(); 
		} else if (resultService.getStatus().equals(ResultRest.STATUS_VOTO)){
			return Response.status(200).entity( resultService  ).build(); 
		} else if (resultService.getStatus().equals(ResultRest.PARAMETER_ERROR)){
			return Response.status(200).entity( resultService  ).build(); 
		} else if (resultService.getStatus().equals(ResultRest.TIME_ERROR)){
			return Response.status(200).entity( resultService  ).build(); 
		}  else {
			return Response.status(400).build(); 	
		}		
	}		
	
	//Contabilizar os votos e dar o resultado da votação na pauta.
	@GET
	@Path("/get/resultado/{codigo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResultado(@PathParam("codigo") int codigo) {
		
		PautaService pautaService = new PautaService();		
		ReportPauta reportPauta = pautaService.reportPauta(codigo);
		
		return Response.status(200).entity( reportPauta ).build(); 	

	}		

}
