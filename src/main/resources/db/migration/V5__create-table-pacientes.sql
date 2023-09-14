create table pacientes(
    id bigint not null auto_increment,
    nombre varchar(100) not null unique,
    email varchar(100) not null unique,
    documento varchar(14) not null,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100) not null,
    numero varchar(20),
    ciudad varchar(100) not null,
    telefono varchar(20) not null,
    activo tinyint not null,
    primary key(id)
);