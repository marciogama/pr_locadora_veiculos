CREATE TABLE carro (
  id int NOT NULL AUTO_INCREMENT,
  modelo varchar(60) DEFAULT NULL,
  placa varchar(7) NOT NULL,
  cor varchar(30) NOT NULL,
  ano int NOT NULL,
  dataAquisicao datetime(6) NOT NULL,
  valorDiaria float(10,2),
  PRIMARY KEY (id),
  CONSTRAINT UK_PLACA UNIQUE (placa)
);

CREATE TABLE cliente (
  cpf varchar(11) NOT NULL,
  nome varchar(60) NOT NULL,
  email varchar(60) NOT NULL,
  PRIMARY KEY (cpf),
  CONSTRAINT UK_EMAIL UNIQUE (email)
);

CREATE TABLE locacao (
  id int NOT NULL AUTO_INCREMENT,
  instanteLocacao datetime(6) NOT NULL,
  instanteDevolucao datetime(6) NOT NULL,
  sede varchar(60) NOT NULL,
  valorTotal float(10.2),
  carroId int NOT NULL,
  cpfId varchar(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (carroId) REFERENCES carro (id),
  FOREIGN KEY (cpfId) REFERENCES cliente (cpf) 
);


INSERT INTO carro (modelo, placa, cor, ano, dataAquisicao, valorDiaria ) VALUES
  ('Chevete','LAL3356','Cinza',1979,'1980-04-21 00:00:00','100.00'),
  ('Fiat Europa','TOC4456','Preto',1974,'1975-05-10 00:00:00','110.00'),
  ('Fiat Uno','BAC3388','Branco',1985,'1986-06-25 00:00:00','120.00'),
  ('Corsa Classic','TTT1528','Cinza',1990,'1992-05-01 00:00:00','130.00');


INSERT INTO cliente (cpf, nome, email) VALUES
  ('11111111111','Bob Brown','bob@gmail.com'),
  ('22222222222','Maria Green','maria@gmail.com'),
  ('33333333333','Alex Grey','alex@gmail.com'),
  ('44444444444','Martha Red','martha@gmail.com');


INSERT INTO locacao (instanteLocacao, instanteDevolucao, sede, valorTotal, carroId, cpfId) VALUES
  ('2020-04-25 07:00:00','2020-04-26 07:00:00','Rio de Janeiro','100.00','1','11111111111'),
  ('2020-04-25 07:10:00','2020-04-26 07:10:00','Rio de Janeiro','200.00','2','22222222222'),
  ('2020-04-25 07:20:00','2020-04-26 07:20:00','Rio de Janeiro','300.00','3','33333333333'), 
  ('2020-04-25 07:30:00','2020-04-26 07:30:00','Rio de Janeiro','400.00','4','44444444444');
