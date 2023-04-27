<h1>Desafio D&D Avanade</h1>

<p> Desafio proposto a fim de verificar e aprofundar comhecimentos em Java.
Foi escrito em Java Springboot 17</p>

<h2> Como rodar? </h2>
<p>Este serviço está exposto na porta 8080</p>
<p>Para abrir o swagger acesse o seguinte endereço em seu navegador: http://localhost:8080/swagger-ui.html</p>

###Rotas Expostas:
* GET/personagem/{id}
* PUT/personagem/{id}
* DELETE/personagem/{id}
* GET/personagem
* POST/personagem

* POST/batalha
* POST/batalha/defesa/{id}
* POST/batalha/ataque/{id}
* GET/calculo_dano/{id}

* GET/historico_batalha
* GET/historico_batalha/{id}

### O corpo das solicitações POST:

{
"nomePersonagem": "string",
"classePersonagem": "Guerreiro",
"tipoPersonagem": "HEROI"
}

###Este aplicativo está executando um banco de dados Microsoft SQl Server
