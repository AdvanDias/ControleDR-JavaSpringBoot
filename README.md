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



create or replace trigger TRIGGRA_CAMP_TOTAL_MOVI_STATUS
after INSERT on CONTA
DECLARE
vid_conta CONTA.id%type;
vitipo_movi CONTA.tip_movimentacoes%type;
BEGIN
select MAX(id) into vid_conta from conta;
select tip_movimentacoes into vitipo_movi from conta where id = vid_conta;
IF(vitipo_movi = 'D')THEN
    UPDATE CONTA SET MOVIMENTACOES_DEB = (MOVIMENTACOES_DEB+1), TOTAL = (select sum(valor) from conta where id = vid_conta) WHERE ID = vid_conta;
ELSE
    UPDATE CONTA SET MOVIMENTACOES_CRE = (MOVIMENTACOES_CRE+1), TOTAL = (select sum(valor) from conta where id = vid_conta) WHERE ID = vid_conta;
END IF;
END;



- **Trigger para popular a tabela  EMPRESA_XPTO, tabela responsável por dados do relatório.**



create or replace trigger TRIGGRA_XPTO_CARGA
AFTER INSERT on CONTA
DECLARE
vid_conta CONTA.id%type;
vid_cli_conta conta.id_cliente%type;

vnome_cli cliente.nome%type;
vdata_cli cliente.data_cadastro%type;
vid_cli cliente.id%type;
BEGIN
select MAX(id) into vid_conta from conta;
select MAX(id_cliente) into vid_cli_conta from conta;

select nome,data_cadastro,id into vnome_cli,vdata_cli,vid_cli from cliente where id = vid_cli_conta;

INSERT INTO EMPRESA_XPTO (id_xpt,cliente_nome,data_clinte,id_cliente,id_conta_clinte,movi_cli,data_cadastro,valor_movi)
values
(sec_xpto_id.nextval,vnome_cli,vdata_cli,vid_cli,vid_conta,1,sysdate,0);
END;



- **View para organização dos dados para o relatório.**



CREATE OR REPLACE VIEW V_CLIENTE_01
AS
select 
cont.id_cliente,
cli.nome as cliente,
to_char(trunc(cli.data_cadastro),'DD/MM/YYYY') as cliente_desde,
ende.rua||' - '||ende.numero||' - '||ende.complemento||' - '||ende.bairro||' - '||ende.cidade||' - '||ende.uf||' - '||ende.cep as endereco,
sum(cont.movimentacoes_cre)as Movimentações_de_crédito,
sum(cont.movimentacoes_deb) as Movimentações_de_débito,
sum(cont.total) as Saldo_inicial
from cliente cli
join endereco ende
on ende.id_clinte = cli.id
join conta cont
on cont.id_cliente = cli.id
GROUP BY cli.nome,cli.data_cadastro,
ende.rua,
ende.numero,
ende.complemento,
ende.bairro,
ende.cidade,
ende.uf,
ende.cep,
cont.id_cliente,
cli.id;