alter table usuarios add ativo tinyint not null;
update usuarios set ativo = 1;