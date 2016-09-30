create table cidade(
	codcidade int not null AUTO_INCREMENT primary key,
    nome varchar(100) not null
    );
alter table Endereco add codcidade int;
alter table Endereco add foreign key 
	(codcidade) references cidade(codcidade);
insert into cidade VALUES(1,'XANXERÊ');

create table UF(
	coduf int not null AUTO_INCREMENT primary key,
    nome varchar(100) not null);
alter table cidade add coduf int;
alter table cidade add foreign key 
		(coduf) references uf(coduf);
insert into uf values(1,'Santa Catarina');

    