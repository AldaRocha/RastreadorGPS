-- ---------------------------------------------------------------------------- --
-- Archivo: 04_DatosBase_RastreadorGPS.sql                      					--
-- Version: 1.0                                                     			--
-- Autor:   Francisco Javier Rocha Aldana   									--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx		--
-- Fecha de elaboracion: 21-06-2023                                 			--
-- ---------------------------------------------------------------------------- --

-- Seleccionamos la base de datos que usaremos --
USE rastreador_gps;

-- Insertar datos a la tabla permiso --
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Inicio", "vista_main.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Empleado", "../empleado/vista_empleado.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Trailer", "../trailer/vista_trailer.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Ruta", "../ruta/vista_ruta.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Monitoreo", "../monitoreo/vista_monitoreo.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Pantalla", "../pantalla/vista_pantalla.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Permiso", "../permiso/vista_permiso.html");
INSERT INTO permiso(nombrePermiso, ruta) VALUES("Cambiar Contraseña", "../cambiarcontraseña/vista_cambiarcontraseña.html");

-- Seleccionar los datos para verificar que todo esta registrado --
SELECT * FROM permiso;

-- Insertar datos a la tabla nivel --
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 1);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 2);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 3);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 4);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 5);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 6);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 7);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Super Administrador", 8);

INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Administrador", 1);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Administrador", 2);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Administrador", 3);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Administrador", 4);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Administrador", 5);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Administrador", 8);

INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Ayudante", 1);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Ayudante", 4);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Ayudante", 5);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Ayudante", 8);

INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Monitorista", 1);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Monitorista", 5);
INSERT INTO nivel(nombreNivel, idPermiso) VALUES("Monitorista", 8);

-- Seleccionar los datos para verificar que todo esta registrado --
SELECT * FROM nivel;

SELECT n.*, p.nombrePermiso, p.ruta FROM nivel n INNER JOIN permiso p ON n.idPermiso=p.idPermiso WHERE n.nombreNivel LIKE "Super Administrador";

SELECT n.*, p.nombrePermiso, p.ruta FROM nivel n INNER JOIN permiso p ON n.idPermiso=p.idPermiso WHERE n.nombreNivel LIKE "Administrador";

SELECT n.*, p.nombrePermiso, p.ruta FROM nivel n INNER JOIN permiso p ON n.idPermiso=p.idPermiso WHERE n.nombreNivel LIKE "Ayudante";

SELECT n.*, p.nombrePermiso, p.ruta FROM nivel n INNER JOIN permiso p ON n.idPermiso=p.idPermiso WHERE n.nombreNivel LIKE "Monitorista";

SELECT nombreNivel FROM nivel GROUP BY nombreNivel;

SELECT nombreNivel FROM nivel WHERE nombreNivel NOT LIKE "Super Administrador" GROUP BY nombreNivel;

-- Insertar datos a la tabla usuario --
INSERT INTO usuario(nombreUsuario, contrasenia, nombreNivel) VALUES("Alda", "aeec78a2ec7aa5c159d7d8e9d4242690611948368c1f55b68528411565f691bf", "Super Administrador");

-- Seleccionar los datos para verificar que todo esta registrado --
SELECT * FROM usuario;

SELECT * FROM usuario u INNER JOIN nivel n ON u.nombreNivel=n.nombreNivel INNER JOIN permiso p ON n.idPermiso=p.idPermiso WHERE u.nombreNivel LIKE "Super Administrador";

-- Insertar datos a la tabla empleado --
INSERT INTO empleado(numeroEmpleado, nombre, primerApellido, segundoApellido, curp, rfc, numeroSeguroSocial, fechaIngreso,
					tipoLicencia, calle, numeroExterior, colonia, ciudad, estado, telCelular, telEmergencia, fotografia, idUsuario)
			  VALUES("0096220623", "Francisco Javier", "Rocha", "Aldana", "ROAF001002HGTCLRA0", "ROAF0010023M9", 25170060096, STR_TO_DATE("22/06/2023", '%d/%m/%Y'),
					 "A", "Cromo", "202", "San Jose del Consuelo", "Leon", "Guanajuato", "4778594709", "4775025051", "", 1);

SELECT * FROM empleado;

-- Insertamos datos a la tabla trailer --
INSERT INTO trailer(placa, marca, modelo, fotografia) VALUES("GUH-659-B", "International", "2023", "");

-- Seleccionamos la tabla para ver que todo esta bien --
SELECT * FROM trailer;

-- Insertamos datos a la tabla ruta --
INSERT INTO ruta(calleOrigen, numeroOrigen, coloniaOrigen, codigoPostalOrigen, calleDestino, numeroDestino, coloniaDestino, codigoPostalDestino, numeroEmpleado, numeroTrailer)
    VALUES("Cromo", "202", "San Jose del Consuelo", "37200", "Universidad Tecnologica", "1494", "La Roncha", "37510", "2543210223", 10000);
    
-- Seleccionamos la tabla para ver que todo esta bien --
SELECT * FROM ruta;

-- Seleccionamos la vista --
SELECT * FROM v_ruta;

UPDATE	empleado
SET		enRuta=1
WHERE	numeroEmpleado LIKE "2543210223";

UPDATE	trailer
SET		enRuta=1
WHERE	numeroTrailer=10000;

ALTER TABLE	permiso
ADD			estatus INT NOT NULL DEFAULT 1;

INSERT INTO clave(nombreClave, detalleClave, clave)
	VALUES("apikey", "Clave utilizada para dar acceso a la api, con esta llave nos aparece el mapa en pantalla", "4zbL6cZV9JJWucjGbYSRsStaUOy9i7vDGrIdDHh3E1Y");

SELECT * FROM clave WHERE nombreClave LIKE "apiKey";

ALTER TABLE ruta
ADD			coordenadasOrigen	VARCHAR(129) NOT NULL DEFAULT "",
ADD			coordenadasDestino	VARCHAR(129) NOT NULL DEFAULT "";

UPDATE ruta
SET    coordenadasOrigen="lat: 21.140152, lng: -101.701148"
WHERE  numeroRuta=20000;

UPDATE ruta
SET    coordenadasDestino="lat: 21.08552, lng: -101.61513"
WHERE  numeroRuta=20000;

ALTER TABLE ruta
ADD			ciudadOrigen	VARCHAR(129) NOT NULL DEFAULT "",
ADD			estadoOrigen	VARCHAR(129) NOT NULL DEFAULT "",
ADD			paisOrigen		VARCHAR(129) NOT NULL DEFAULT "",
ADD			ciudadDestino	VARCHAR(129) NOT NULL DEFAULT "",
ADD			estadoDestino	VARCHAR(129) NOT NULL DEFAULT "",
ADD			paisDestino		VARCHAR(129) NOT NULL DEFAULT "";

UPDATE	ruta
SET		ciudadOrigen="LEON";

UPDATE	ruta
SET		estadoOrigen="GUANAJUATO";

UPDATE	ruta
SET		paisOrigen="MEXICO";

UPDATE	ruta
SET		ciudadDestino="LEON";

UPDATE	ruta
SET		estadoDestino="GUANAJUATO";

UPDATE	ruta
SET		paisDestino="MEXICO";

UPDATE 	permiso
SET		estatus=0
WHERE	nombrePermiso LIKE "Permiso";