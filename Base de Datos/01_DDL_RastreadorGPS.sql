-- ---------------------------------------------------------------------------- --
-- Archivo: 01_DDL_RastreadorGPS.sql                                			--
-- Version: 1.0                                                     			--
-- Autor:   Francisco Javier Rocha Aldana   									--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx		--
-- Fecha de elaboracion: 21-06-2023                                 			--
-- ---------------------------------------------------------------------------- --

-- Eliminamos la base de datos si existe --
DROP DATABASE IF EXISTS rastreador_gps;

-- Creamos la base de datos --
CREATE DATABASE rastreador_gps;

-- Seleccionamos la base de datos que usaremos --
USE rastreador_gps;

-- 		Creacion de la tabla permisos 		--
-- Esta tabla tendre los labels y las rutas --
-- que se pondran en el nav del html, se 	--
-- consultaran dependiendo del usuario y su --
-- nivel --
CREATE TABLE permiso(
	idPermiso				INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 1
    nombrePermiso			VARCHAR(129) NOT NULL,							-- 2
    ruta					VARCHAR(129) NOT NULL							-- 3
);

-- 		Creacion de la tabla nivel 		--
-- Esta tabla contiene el tipo de privilegios --
-- que tendra el usuario, dependiendo de los  --
-- privilegios, se hace la consulta de los 	  --
-- permisos y los muestra en la interfaz --
CREATE TABLE nivel(
	idNivel					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 1
    nombreNivel				VARCHAR(45) NOT NULL,							-- 2
    idPermiso				INT NOT NULL,									-- 3
    CONSTRAINT fk_nivel_idPermiso FOREIGN KEY (idPermiso)
				REFERENCES permiso(idPermiso)
);

-- Es necesario para que funcione la tabla --
ALTER TABLE nivel ADD INDEX idx_nombreNivel (nombreNivel);

-- 		Creacion de la tabla usuario 		--
-- Esta tabla contendra la informacion de 	--
-- seguridad de cada usuario y su nivel --
CREATE TABLE usuario(
	idUsuario				INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 1
    nombreUsuario			VARCHAR(129) UNIQUE NOT NULL,					-- 2
    contrasenia				VARCHAR(129) NOT NULL,							-- 3
    nombreNivel				VARCHAR(45) NOT NULL,							-- 4
    lastToken				VARCHAR(65) NOT NULL DEFAULT '',				-- 5
    dateLastToken			DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,	-- 6
    CONSTRAINT fk_usuario_nombreNivel FOREIGN KEY (nombreNivel)
				REFERENCES nivel(nombreNivel)
);

-- 		Creacion de la tabla empleado 		--
-- Esta tabla almacena la informacion de	--
-- los empleados --
CREATE TABLE empleado(
	numeroEmpleado			VARCHAR(15) NOT NULL PRIMARY KEY,				-- 1
    nombre					VARCHAR(129) NOT NULL,							-- 2
    primerApellido			VARCHAR(50) NOT NULL,							-- 3
    segundoApellido			VARCHAR(50) NOT NULL,							-- 4
    curp					VARCHAR(30) NOT NULL,							-- 5
    rfc						VARCHAR(20) NOT NULL,							-- 6
    numeroSeguroSocial		BIGINT NOT NULL,								-- 7
    fechaIngreso			DATE NOT NULL,									-- 8
    fechaBaja				DATE,											-- 9
    tipoLicencia			VARCHAR(5) NOT NULL,							-- 10
    calle					VARCHAR(129) NOT NULL,							-- 11
    numeroExterior			VARCHAR(20) NOT NULL,							-- 12
    colonia					VARCHAR(40) NOT NULL,							-- 13
    ciudad					VARCHAR(40) NOT NULL,							-- 14
    estado					VARCHAR(40) NOT NULL,							-- 15
    telCelular				VARCHAR(20) NOT NULL,							-- 16
    telEmergencia			VARCHAR(20) NOT NULL,							-- 17
    fotografia				LONGTEXT NOT NULL,								-- 18
    estatus					INT NOT NULL DEFAULT 1,							-- 19
    enRuta					INT NOT NULL DEFAULT 0,							-- 20
    idUsuario				INT NOT NULL,									-- 21
    CONSTRAINT fk_empleado_idUsuario FOREIGN KEY (idUsuario)
				REFERENCES usuario(idUsuario)
);

-- 		Creacion de la tabla trailer 		--
-- Esta tabla almacenará la informacion de  --
-- todos los traileres que estaran en ruta --
CREATE TABLE trailer(
	numeroTrailer			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 1
    placa					VARCHAR(20) NOT NULL,							-- 2
    marca					VARCHAR(40) NOT NULL,							-- 3
    modelo					VARCHAR(40) NOT NULL,							-- 4
    fotografia				LONGTEXT NOT NULL,								-- 5
    estatus					INT NOT NULL DEFAULT 1,							-- 6
    enRuta					INT NOT NULL DEFAULT 0							-- 7
)AUTO_INCREMENT=10000;

-- 		Creacion de la tabla ruta 		--
-- Esta tabla almacenará la informacion --
-- de las rutas establecidas por un 	--
-- usuario que tenga los permisos		--
-- necesarios --
CREATE TABLE ruta(
	numeroRuta				INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 1
    calleOrigen				VARCHAR(129) NOT NULL,							-- 2
    numeroOrigen			VARCHAR(20) NOT NULL,							-- 3
    coloniaOrigen			VARCHAR(40) NOT NULL,							-- 4
    codigoPostalOrigen		VARCHAR(25) NOT NULL,							-- 5
    calleDestino			VARCHAR(129) NOT NULL,							-- 6
    numeroDestino			VARCHAR(20) NOT NULL,							-- 7
    coloniaDestino			VARCHAR(40) NOT NULL,							-- 8
    codigoPostalDestino		VARCHAR(25) NOT NULL,							-- 9
    estatus					INT NOT NULL DEFAULT 1,							-- 10
    numeroEmpleado			VARCHAR(15) NOT NULL,							-- 11
    numeroTrailer			INT NOT NULL,									-- 12
    CONSTRAINT fk_ruta_numeroEmpleado FOREIGN KEY (numeroEmpleado)
				REFERENCES empleado(numeroEmpleado),
	CONSTRAINT fk_ruta_numeroTrailer FOREIGN KEY (numeroTrailer)
				REFERENCES trailer(numeroTrailer)
)AUTO_INCREMENT=20000;

-- 		Creacion de la tabla clave  		--
-- Esta tabla almacenará la informacion de  --
-- las claves necesarias para el 			--
-- funionamineto de la api	--
CREATE TABLE clave(
	idClave			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,				-- 1
    nombreClave		VARCHAR(40) NOT NULL,									-- 2
    detalleClave	VARCHAR(129) NOT NULL,									-- 3
    clave			LONGTEXT NOT NULL										-- 4
);