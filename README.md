Desafio - PautaApp

Neste exemplo usamos uma arquitetura REST provendo realizar e atender os requisitos, maximizando o tempo e aproveitando minhas experiências. Desenvolvido usando plataforma eclipse, maven, postgres e tomcat (não embutido).

*Para rodar basta adicionar o .war na Web Module.
*BANCO DE DADOS ESTA EM NUVEM PUBLICA

Arquitetura Necessária
- Java 1.8
- Postgres
- TomCat7 ou 8

EndPoints

/rest/pauta/criar - POST
{ "codigo": 1, "titulo" : "ABRIR NOVAS COPERATIVAS", "descricao" : "Abrir coperativas em cidades com menos de 20 Mil habitantes?" }

/rest/pauta/abrir - POST
{ "codigo": 1 }

/rest/votar - POST
{ "pauta": 1, "associado" : 81049201035, "voto" : "SIM" }

/rest/get/resultado/1 - GET
{
    "pauta": {
        "codigo": 1,
        "data": 12/01/2021,
        "descricao": "Abrir coperativas em cidades com menos de 20 Mil habitantes?",
        "titulo": "ABRIR NOVAS COPERATIVAS",
        "votarPauta": []
    },
    "totalVotos": 3,
    "votosNao": 0,
    "votosSim": 3
}

