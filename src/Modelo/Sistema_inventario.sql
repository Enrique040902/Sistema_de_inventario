DROP DATABASE IF EXISTS sistema_inventario;
CREATE DATABASE Sistema_inventario;
USE Sistema_inventario;

CREATE TABLE Usuarios (
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Username VARCHAR(30) NOT NULL,
    
    PRIMARY KEY (ID)
);

ALTER TABLE Usuarios ADD UNIQUE idx_username (Username); 

CREATE TABLE Credenciales (
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    ID_usuario INT UNSIGNED NOT NULL,
	Username VARCHAR(30) NOT NULL,
	Contrasenia VARCHAR(30) NOT NULL,
    Privilegio VARCHAR(20) NOT NULL,
    
	PRIMARY KEY (ID),
    FOREIGN KEY (ID_usuario) REFERENCES Usuarios (ID)
);

CREATE TABLE Nombre_Usuario (
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    ID_usuario INT UNSIGNED NOT NULL,
    Nombre VARCHAR(30) NOT NULL,
    ApellidoPat VARCHAR(30) NOT NULL,
    ApellidoMat VARCHAR(30) NOT NULL,
    
    PRIMARY KEY (ID),
    FOREIGN KEY (ID_usuario) REFERENCES Usuarios(ID)
);

CREATE TABLE Proveedores (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    Nombre VARCHAR(30) NOT NULL,
    Telefono VARCHAR(15) NOT NULL,
    ID_direccion INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (ID)
);

CREATE TABLE Direccion_proveedores (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    Nombre_calle VARCHAR(30) NOT NULL,
    Numero_calle VARCHAR(30),
    Codigo_postal VARCHAR(30) NOT NULL,
    Estado VARCHAR(30) NOT NULL,
    ID_proveedor INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (ID)
);

ALTER TABLE Proveedores ADD FOREIGN KEY (ID_direccion) REFERENCES Direccion_proveedores (ID);

CREATE TABLE Productos (
	ID VARCHAR(80) NOT NULL,
    Nombre_producto VARCHAR(30) NOT NULL,
    Marca VARCHAR(30) NOT NULL,
    Descripcion_corta VARCHAR(100) NOT NULL,
    Precio_venta VARCHAR(10) NOT NULL,
    Precio_compra VARCHAR(10) NOT NULL,
    Stock VARCHAR(15) NOT NULL,
    ID_proveedor INT UNSIGNED NOT NULL,
    ID_departamento INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (ID),
    FOREIGN KEY (ID_proveedor) REFERENCES Proveedores(ID)
);

CREATE TABLE Departamentos (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    Nombre_departamento VARCHAR(30) NOT NULL,
    
    PRIMARY KEY (ID)
);

ALTER TABLE Productos ADD foreign key (ID_departamento) references Departamentos (ID);

CREATE TABLE Salidas (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    ID_proveedor INT UNSIGNED NOT NULL,
    ID_cliente INT UNSIGNED NOT NULL,
    Fecha DATE NOT NULL, 
    Cantidad_producto INT NOT NULL,
    
    PRIMARY KEY (ID),
    foreign key (ID_proveedor) references Proveedores (ID)
);



CREATE TABLE Entradas (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    ID_proveedor INT UNSIGNED NOT NULL,
    ID_producto VARCHAR(30) NOT NULL,
    Fecha DATE NOT NULL, 
    Cantidad_producto INT NOT NULL,
    
    PRIMARY KEY (ID),
    foreign key (ID_proveedor) references Proveedores (ID),
    foreign key (ID_producto) references Productos (ID)
);

CREATE TABLE Clientes (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    Nombre VARCHAR(30) NOT NULL, 
    Telefono VARCHAR(30) NOT NULL,
	ID_direccionC INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (ID)
);

ALTER TABLE Salidas ADD foreign key (ID_cliente) references Clientes (ID);



CREATE TABLE Direccion_cliente (
	ID INT UNSIGNED AUTO_INCREMENT NOT NULL,
    Nombre_calle VARCHAR(30) NOT NULL,
    Numero_calle VARCHAR(30),
    Estado VARCHAR(30) NOT NULL,
    
    PRIMARY KEY (ID)
);

ALTER TABLE Clientes add foreign key (ID_direccionC) references Direccion_cliente (ID);

-- Inserción de datos del administrador.
insert into Usuarios(Username) values('admin');
insert into Credenciales(ID_usuario, username, contrasenia, privilegio) values (last_insert_id(), 'admin', 'admin', 'Administrador');
insert into Nombre_usuario(ID_usuario, Nombre, ApellidoPat, ApellidoMat) values (last_insert_id(), 'Dilan', 'Sanchez', 'Morales');

insert into Usuarios(Username) values('usuario1');
insert into Credenciales(username, contrasenia, privilegio) values ('usuario3', '123', 'Empleado');
insert into Nombre_usuario(Nombre, ApellidoPat, ApellidoMat) values ('Dilan', 'Sanchez', 'Morales');