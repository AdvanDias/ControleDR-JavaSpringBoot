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



- **Trigger para popular a tabela  EMPRESA_XPTO, tabela responsável por dados do relatório.**



create or replace trigger TRIGGRA_XPTO_CARGA&nbsp;<br/>
AFTER INSERT on CONTA&nbsp;<br/>
DECLARE&nbsp;<br/>
vid_conta CONTA.id%type;&nbsp;<br/>
vid_cli_conta conta.id_cliente%type;&nbsp;<br/>

vnome_cli cliente.nome%type;&nbsp;<br/>
vdata_cli cliente.data_cadastro%type;&nbsp;<br/>
vid_cli cliente.id%type;&nbsp;<br/>
BEGIN&nbsp;<br/>
select MAX(id) into vid_conta from conta;&nbsp;<br/>
select MAX(id_cliente) into vid_cli_conta from conta;&nbsp;<br/>

select nome,data_cadastro,id into vnome_cli,vdata_cli,vid_cli from cliente where id = vid_cli_conta;&nbsp;<br/>

INSERT INTO EMPRESA_XPTO (id_xpt,cliente_nome,data_clinte,id_cliente,id_conta_clinte,movi_cli,data_cadastro,valor_movi)&nbsp;<br/>
values&nbsp;<br/>
(sec_xpto_id.nextval,vnome_cli,vdata_cli,vid_cli,vid_conta,1,sysdate,0);&nbsp;<br/>
END;&nbsp;<br/>



- **View para organização dos dados para o relatório.**



CREATE OR REPLACE VIEW V_CLIENTE_01&nbsp;<br/>
AS&nbsp;<br/>
select &nbsp;<br/>
cont.id_cliente,&nbsp;<br/>
cli.nome as cliente,&nbsp;<br/>
to_char(trunc(cli.data_cadastro),'DD/MM/YYYY') as cliente_desde,&nbsp;<br/>
ende.rua||' - '||ende.numero||' - '||ende.complemento||' - '||ende.bairro||' - '||ende.cidade||' - '||ende.uf||' - '||ende.cep as endereco,&nbsp;<br/>
sum(cont.movimentacoes_cre)as Movimentações_de_crédito,&nbsp;<br/>
sum(cont.movimentacoes_deb) as Movimentações_de_débito,&nbsp;<br/>
sum(cont.total) as Saldo_inicial&nbsp;<br/>
from cliente cli&nbsp;<br/>
join endereco ende&nbsp;<br/>
on ende.id_clinte = cli.id&nbsp;<br/>
join conta cont&nbsp;<br/>
on cont.id_cliente = cli.id&nbsp;<br/>
GROUP BY cli.nome,cli.data_cadastro,&nbsp;<br/>
ende.rua,&nbsp;<br/>
ende.numero,&nbsp;<br/>
ende.complemento,&nbsp;<br/>
ende.bairro,&nbsp;<br/>
ende.cidade,&nbsp;<br/>
ende.uf,&nbsp;<br/>
ende.cep,&nbsp;<br/>
cont.id_cliente,&nbsp;<br/>
cli.id;&nbsp;&nbsp;<br/>