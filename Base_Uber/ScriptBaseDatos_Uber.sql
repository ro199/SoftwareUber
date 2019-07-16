-- drop database uber;
create database uber;
use uber;

/*----------------------------------------------------------*/
/*---------------- Creación tablas--------------------------*/
/*----------------------------------------------------------*/

create table usuario(
	idTipo int primary key not null auto_increment,
    usuario varchar(30) not null,
    contrasena varchar(30),
	tipoUsuario varchar(20) not null
);

create table cliente(
	cedulaCliente varchar(10) primary key not null,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    correoU varchar(50) not null,
    telefonoU varchar(15) not null,
    idTipo int not null,
    FOREIGN KEY (idTipo) REFERENCES usuario(idTipo)
);


create table tarjeta(
	idTarjeta int auto_increment primary key,
    nombreTarjeta varchar(30) not null,
    fechaVencimiento varchar(15) not null,
    cvv int not null
);

create table usuario_tarjeta(
	cedulaUsuario varchar(10)  not null,
    idTarjeta int,
    cedulaCliente varchar(10) not null,
    FOREIGN KEY (cedulaCliente) REFERENCES cliente(cedulaCliente),
    FOREIGN KEY (idTarjeta) REFERENCES tarjeta(idTarjeta)

);

create table vehiculo(
	matricula varchar(15) not null primary key,
    marca varchar(30) not null,
    color varchar(30) not null,
    foto varchar(100)
);

create table asociacion(
	idAsociacion int primary key not null auto_increment,
    asociacion varchar(30) not null
);

create table conductor(
	cedulaConductor varchar(10) primary key not null,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    correoC varchar(50) not null,
    celularC varchar(10) not null,
    fechaNacimiento varchar(15) not null,
	direccion varchar(100) not null,
    nacionalidad varchar(50) not null,
    matricula varchar(15) not null,
    idAsociacion int not null,
    idTipo int not null,
    FOREIGN KEY (matricula) REFERENCES vehiculo(matricula),
    FOREIGN KEY (idAsociacion) REFERENCES asociacion(idAsociacion),
    FOREIGN KEY (idTipo) REFERENCES usuario(idTipo)
);




/*----------------------------------------------------------*/
/*-------------- Selects de comprobación--------------------*/
/*----------------------------------------------------------*/

select * from usuario;
select * from cliente;
select * from tarjeta;
select * from usuario_tarjeta;
select * from conductor;
select * from vehiculo;
select * from asociacion;

