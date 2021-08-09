# ControleDR-JavaSpringBoot

### Objetivo 

Desenvolver um sistema para controlar as despesas e receitas de clientes.



### Nesse projeto foi utilizado as seguintes abordagens:

- Desenvolvido com **Java** **SpringBoot**, **Maven**, **JasperReports**.

- Modelo de dados para o mapeamento de entidades em bancos de dados **H2** e **Oracle**.

- Desenvolvimento de operações de CRUD para a aplicação **(Cadastro, leitura, atualização e remoção)**.

- Relação de cada uma das operações acima com o padrão arquitetural **REST**.

- Estrutura desenvolvida com base em **MVC**

  

### São necessários os seguintes pré-requisitos para a execução do projeto:

- Java 11 ou versões superiores.
- Maven 3.6.3 ou versões superiores.
- Intellj IDEA Community Edition ou sua IDE favorita.
- Postman para consumir a API.



### **Comandos sql para eventos no banco de dados.**



- **Trigger responsável pela atualização dos campos da tabela conta.**



create or replace trigger TRIGGRA_CAMP_TOTAL_MOVI_STATUS <br/>&nbsp;
after INSERT on CONTA<br/>&nbsp;
DECLARE&nbsp;<br/>
vid_conta CONTA.id%type;&nbsp;<br/>itipo_movi CONTA.tip_movimentacoes%type;&nbsp;<br/>
BEGIN&nbsp;<br/>
select MAX(id) into vid_conta from conta;&nbsp;<br/>
select tip_movimentacoes into vitipo_movi from conta where id = vid_conta;&nbsp;<br>
IF(vitipo_movi = 'D')THEN&nbsp;<br/>
    UPDATE CONTA SET MOVIMENTACOES_DEB = (MOVIMENTACOES_DEB+1), TOTAL = (select sum(valor) from conta where id = vid_conta) WHERE ID = vid_conta;&nbsp;<br/>
ELSE&nbsp;<br>
    UPDATE CONTA SET MOVIMENTACOES_CRE = (MOVIMENTACOES_CRE+1), TOTAL = (select sum(valor) from conta where id = &nbsp;vid_conta) WHERE ID = vid_conta;<br/>
END IF;&nbsp;<br/>
END;&nbsp;<br/>



- **Function responsável pelo cálculo da quantidade de operações (entradas e saídas).**



CREATE OR REPLACE FUNCTION calculo_empresa_xpto<br/>
(pCod_Curso NUMBER) RETURN NUMBER<br/>
AS<br/>
  vValor FLOAT;<br/>
BEGIN<br/>
 vValor := 0;<br/>
 IF(pCod_Curso <= 10)THEN<br/>
    vValor := pCod_Curso * 1;<br/>
 ELSIF(pCod_Curso > 10 and pCod_Curso <= 20) THEN<br/>
    vValor := pCod_Curso * 0.75;<br/>
 ELSIF(pCod_Curso > 20) THEN<br/>
    vValor := pCod_Curso * 0.50;<br/>
 END IF;<br/>
  RETURN(vValor);<br/>
END;<br/>



- **View para o relatório dos clientes.**



CREATE OR REPLACE VIEW V_CLIENTE_01<br/>
AS<br/>
select <br/>
cont.id_cliente,<br/>
cli.nome as cliente,<br/>
to_char(trunc(cli.data_cadastro),'DD/MM/YYYY') as cliente_desde,<br/>
ende.rua||' - '||ende.numero||' - '||ende.complemento||' - '||ende.bairro||' - '||ende.cidade||' - '||ende.uf||' - '||ende.cep as endereco,<br/>
sum(cont.movimentacoes_cre)as Movimentações_de_crédito,<br/>
sum(cont.movimentacoes_deb) as Movimentações_de_débito,<br/>
(sum(movimentacoes_deb)+sum(movimentacoes_cre)) as Total_de_movimentações,<br/>
calculo_empresa_xpto((sum(movimentacoes_deb)+sum(movimentacoes_cre)))as Valor_pago_pelas_movimentações,<br/>
sum(cont.total) as Saldo_inicial,<br/>
(sum(cont.total) - calculo_empresa_xpto((sum(movimentacoes_deb)+sum(movimentacoes_cre)))) as Saldo_Atual<br/>
from cliente cli<br/>
join endereco ende<br/>
on ende.id_clinte = cli.id<br/>
join conta cont<br/>
on cont.id_cliente = cli.id<br/>
GROUP BY cli.nome,cli.data_cadastro,<br/>
ende.rua,<br/>
ende.numero,<br/>
ende.complemento,<br/>
ende.bairro,<br/>
ende.cidade,<br/>
ende.uf,<br/>
ende.cep,<br/>
cont.id_cliente,<br/>
cli.id;<br/>