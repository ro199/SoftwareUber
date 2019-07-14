
use uber;
/*----------------------------------------------------------*/
/*---------------- Creación tablas--------------------------*/
/*----------------------------------------------------------*/

create table usuario(
	cedulaUsuario varchar(10) primary key not null,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    correoU varchar(50) not null,
    telefonoU varchar(15) not null,
    contrasenaU varchar(25) not null
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
    
    FOREIGN KEY (cedulaUsuario) REFERENCES usuario(cedulaUsuario),
    FOREIGN KEY (idTarjeta) REFERENCES tarjeta(idTarjeta)

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
    contrasenaC varchar(25) not null,
    matricula varchar(15) not null,
    FOREIGN KEY (matricula) REFERENCES vehiculo(matricula)
);

create table vehiculo(
	matricula varchar(15) not null primary key,
    marca varchar(30) not null,
    color varchar(30) not null,
    foto varchar(100)
);


/*----------------------------------------------------------*/
/*-------------- Selects de comprobación--------------------*/
/*----------------------------------------------------------*/

select * from usuario;
select * from tarjeta;
select * from usuario_tarjeta;
select * from conductor;
select * from vehiculo;




