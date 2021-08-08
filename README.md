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



create or replace trigger TRIGGRA_CAMP_TOTAL_MOVI_STATUS&nbsp;
after INSERT on CONTA&nbsp;
DECLARE&nbsp;
vid_conta CONTA.id%type;&nbsp;
vitipo_movi CONTA.tip_movimentacoes%type;&nbsp;
BEGIN&nbsp;
select MAX(id) into vid_conta from conta;&nbsp;
select tip_movimentacoes into vitipo_movi from conta where id = vid_conta;&nbsp;
IF(vitipo_movi = 'D')THEN&nbsp;
    UPDATE CONTA SET MOVIMENTACOES_DEB = (MOVIMENTACOES_DEB+1), TOTAL = (select sum(valor) from conta where id = &nbsp;vid_conta) WHERE ID = vid_conta;&nbsp;
ELSE&nbsp;
    UPDATE CONTA SET MOVIMENTACOES_CRE = (MOVIMENTACOES_CRE+1), TOTAL = (select sum(valor) from conta where id = &nbsp;vid_conta) WHERE ID = vid_conta;&nbsp;
END IF;&nbsp;
END;&nbsp;



- **Trigger para popular a tabela  EMPRESA_XPTO, tabela responsável por dados do relatório.**



create or replace trigger TRIGGRA_XPTO_CARGA&nbsp;
AFTER INSERT on CONTA&nbsp;
DECLARE&nbsp;
vid_conta CONTA.id%type;&nbsp;
vid_cli_conta conta.id_cliente%type;&nbsp;

vnome_cli cliente.nome%type;&nbsp;
vdata_cli cliente.data_cadastro%type;&nbsp;
vid_cli cliente.id%type;&nbsp;
BEGIN&nbsp;
select MAX(id) into vid_conta from conta;&nbsp;
select MAX(id_cliente) into vid_cli_conta from conta;&nbsp;

select nome,data_cadastro,id into vnome_cli,vdata_cli,vid_cli from cliente where id = vid_cli_conta;&nbsp;

INSERT INTO EMPRESA_XPTO (id_xpt,cliente_nome,data_clinte,id_cliente,id_conta_clinte,movi_cli,data_cadastro,valor_movi)&nbsp;
values&nbsp;
(sec_xpto_id.nextval,vnome_cli,vdata_cli,vid_cli,vid_conta,1,sysdate,0);&nbsp;
END;&nbsp;



- **View para organização dos dados para o relatório.**



CREATE OR REPLACE VIEW V_CLIENTE_01&nbsp;
AS&nbsp;
select &nbsp;
cont.id_cliente,&nbsp;
cli.nome as cliente,&nbsp;
to_char(trunc(cli.data_cadastro),'DD/MM/YYYY') as cliente_desde,&nbsp;
ende.rua||' - '||ende.numero||' - '||ende.complemento||' - '||ende.bairro||' - '||ende.cidade||' - '||ende.uf||' - '||ende.cep &nbsp;as endereco,&nbsp;
sum(cont.movimentacoes_cre)as Movimentações_de_crédito,&nbsp;
sum(cont.movimentacoes_deb) as Movimentações_de_débito,&nbsp;
sum(cont.total) as Saldo_inicial&nbsp;
from cliente cli&nbsp;
join endereco ende&nbsp;
on ende.id_clinte = cli.id&nbsp;
join conta cont&nbsp;
on cont.id_cliente = cli.id&nbsp;
GROUP BY cli.nome,cli.data_cadastro,&nbsp;
ende.rua,&nbsp;
ende.numero,&nbsp;
ende.complemento,&nbsp;
ende.bairro,&nbsp;
ende.cidade,&nbsp;
ende.uf,&nbsp;
ende.cep,&nbsp;
cont.id_cliente,&nbsp;
cli.id;&nbsp;&nbsp;