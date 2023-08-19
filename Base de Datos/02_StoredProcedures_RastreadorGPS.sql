-- ---------------------------------------------------------------------------- --
-- Archivo: 02_StoredProcedures_RastreadorGPS.sql                      			--
-- Version: 1.0                                                     			--
-- Autor:   Francisco Javier Rocha Aldana   									--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx		--
-- Fecha de elaboracion: 21-06-2023                                 			--
-- ---------------------------------------------------------------------------- --

-- Seleccionamos la base de datos que usaremos --
USE rastreador_gps;

-- Stored Procedure para generar el token del empleado --
DROP PROCEDURE IF EXISTS generarToken;
DELIMITER $$
CREATE PROCEDURE generarToken(	-- Recibimos los datos del usuario --
								IN	var_idUsuario	INT,			-- 1
								IN	var_token		VARCHAR(65)		-- 2
)
BEGIN
	-- Actualizamos la tabla usuario --
	UPDATE	usuario
    
    -- Aplicamos los cambios --
    SET		lastToken=var_token,
			dateLastToken=CURRENT_TIMESTAMP
            
	-- Aplicamos un filtro --
    WHERE	idUsuario=var_idUsuario;
END $$
DELIMITER ;

-- Stored Procedure para registrar un empleado --
DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(	-- Recibimos los datos de seguridad primero --
									IN	var_nombreUsuario		VARCHAR(129),	-- 1
									IN	var_contrasenia			VARCHAR(129),	-- 2
									IN	var_nombreNivel			VARCHAR(45),	-- 3
                                    
                                    -- Recibimos los datos del empleado --
                                    IN	var_nombre				VARCHAR(129),	-- 4
									IN	var_primerApellido		VARCHAR(50),	-- 5
									IN	var_segundoApellido		VARCHAR(50),	-- 6
									IN	var_curp				VARCHAR(30),	-- 7
									IN	var_rfc					VARCHAR(20),	-- 8
									IN	var_numeroSeguroSocial	BIGINT,			-- 9
									IN	var_fechaIngreso		VARCHAR(15),	-- 10
									IN	var_tipoLicencia		VARCHAR(5),		-- 11
									IN	var_calle				VARCHAR(129),	-- 12
									IN	var_numeroExterior		VARCHAR(20),	-- 13
									IN	var_colonia				VARCHAR(40),	-- 14
									IN	var_ciudad				VARCHAR(40),	-- 15
									IN	var_estado				VARCHAR(40),	-- 16
									IN	var_telCelular			VARCHAR(20),	-- 17
									IN	var_telEmergencia		VARCHAR(20),	-- 18
									IN	var_fotografia			LONGTEXT,		-- 19

									-- Para los datos de salida --
									OUT	var_idUsuario			INT,			-- 20
                                    OUT	var_numeroEmpleado		VARCHAR(15)		-- 21
)
BEGIN
	-- Empezamos insertando los datos de seguridad --
    INSERT INTO usuario(nombreUsuario, contrasenia, nombreNivel) 
				VALUES(var_nombreUsuario, var_contrasenia, var_nombreNivel);
                
	-- Almacenamos el ultimo id insertado --
    SET var_idUsuario=LAST_INSERT_ID();
    
    -- Preparamos el numero de empleado --
    SET var_numeroEmpleado=CONCAT((SUBSTRING(var_numeroSeguroSocial, -4)), (CONCAT(SUBSTRING(var_fechaIngreso, 1, 2), SUBSTRING(var_fechaIngreso, 4, 2), SUBSTRING(var_fechaIngreso, -2))));
    
    -- Insertamos los datos del empleado --
    INSERT INTO empleado(numeroEmpleado, nombre, primerApellido, segundoApellido, curp, rfc, numeroSeguroSocial, fechaIngreso, tipoLicencia, 
						calle, numeroExterior, colonia, ciudad, estado, telCelular, telEmergencia, fotografia, idUsuario) 
				VALUES(var_numeroEmpleado, var_nombre, var_primerApellido, var_segundoApellido, var_curp, var_rfc, var_numeroSeguroSocial, 
						STR_TO_DATE(var_fechaIngreso, '%d/%m/%Y'), var_tipoLicencia, var_calle, var_numeroExterior, var_colonia, var_ciudad, var_estado, var_telCelular, 
						var_telEmergencia, var_fotografia, var_idUsuario);
END $$
DELIMITER ;

-- Stored Procedure para actualizar un empleado --
DROP PROCEDURE IF EXISTS actualizarEmpleado;
DELIMITER $$
CREATE PROCEDURE actualizarEmpleado(	-- Recibimos los datos de seguridad primero --
									IN	var_nombreUsuario		VARCHAR(129),	-- 1
									IN	var_contrasenia			VARCHAR(129),	-- 2
									IN	var_nombreNivel			VARCHAR(45),	-- 3
                                    
                                    -- Recibimos los datos del empleado --
                                    IN	var_nombre				VARCHAR(129),	-- 4
									IN	var_primerApellido		VARCHAR(50),	-- 5
									IN	var_segundoApellido		VARCHAR(50),	-- 6
									IN	var_curp				VARCHAR(30),	-- 7
									IN	var_rfc					VARCHAR(20),	-- 8
									IN	var_numeroSeguroSocial	BIGINT,			-- 9
									IN	var_fechaIngreso		VARCHAR(15),	-- 10
									IN	var_tipoLicencia		VARCHAR(5),		-- 11
									IN	var_calle				VARCHAR(129),	-- 12
									IN	var_numeroExterior		VARCHAR(20),	-- 13
									IN	var_colonia				VARCHAR(40),	-- 14
									IN	var_ciudad				VARCHAR(40),	-- 15
									IN	var_estado				VARCHAR(40),	-- 16
									IN	var_telCelular			VARCHAR(20),	-- 17
									IN	var_telEmergencia		VARCHAR(20),	-- 18
									IN	var_fotografia			LONGTEXT,		-- 19

									-- Recibimos los datos con los que actualizaremos --
									IN	var_idUsuario			INT,			-- 20
                                    IN	var_numeroEmpleado		VARCHAR(15)		-- 21
)
BEGIN
	-- Empezamos actualizando los datos de seguridad --
    UPDATE	usuario
	   SET	nombreUsuario=var_nombreUsuario, contrasenia=var_contrasenia, nombreNivel=var_nombreNivel
	 WHERE	idUsuario=var_idUsuario;
                
	-- Actualizamos los datos del empleado --
    UPDATE	empleado
	   SET	numeroEmpleado=CONCAT(SUBSTRING(var_numeroSeguroSocial, -4), SUBSTRING(var_fechaIngreso, 1, 2), SUBSTRING(var_fechaIngreso, 4, 2), 
			SUBSTRING(var_fechaIngreso, -2)), nombre=var_nombre, primerApellido=var_primerApellido, segundoApellido=var_segundoApellido, 
            curp=var_curp, rfc=var_rfc, numeroSeguroSocial=var_numeroSeguroSocial, fechaIngreso=STR_TO_DATE(var_fechaIngreso, '%d/%m/%Y'), 
            tipoLicencia=var_tipoLicencia, calle=var_calle, numeroExterior=var_numeroExterior, colonia=var_colonia, ciudad=var_ciudad, 
            estado=var_estado, telCelular=var_telCelular, telEmergencia=var_telEmergencia, fotografia=var_fotografia
	 WHERE	numeroEmpleado LIKE var_numeroEmpleado;
END $$
DELIMITER ;

-- Stored Procedure para cambiar el estatus del empleado --
DROP PROCEDURE IF EXISTS eliminarEmpleado;
DELIMITER $$
CREATE PROCEDURE eliminarEmpleado( -- Recibimos los datos del empleado --
									IN	var_numeroEmpleado	VARCHAR(15)			-- 1
)
BEGIN
	UPDATE	empleado
    SET		estatus=0,
			fechaBaja=CURRENT_TIMESTAMP
    WHERE	numeroEmpleado LIKE var_numeroEmpleado;
END $$
DELIMITER ;

-- Stored Procedure para cambiar la contraseña del empleado --
DROP PROCEDURE IF EXISTS cambiarContraseña;
DELIMITER $$
CREATE PROCEDURE cambiarContraseña( -- Recibimos los datos del empleado --
									IN	var_idUsuario		INT,					-- 1
                                    IN	var_contrasenia		VARCHAR(129)			-- 2
)
BEGIN
	UPDATE	usuario
    SET		contrasenia=var_contrasenia
    WHERE	idUsuario=var_idUsuario;
END $$
DELIMITER ;

-- Stored Procedure para insertar un trailer --
DROP PROCEDURE IF EXISTS insertarTrailer;
DELIMITER $$
CREATE PROCEDURE insertarTrailer( -- Recibimos los datos del trailer --
									IN	var_placa			VARCHAR(20),	-- 1
									IN	var_marca			VARCHAR(40),	-- 2
									IN	var_modelo			VARCHAR(40),	-- 3
									IN	var_fotografia		LONGTEXT,		-- 4

									OUT	var_numeroTrailer	INT				-- 5
)
BEGIN
	-- Insertamos los datos del trailer --
    INSERT INTO trailer(placa, marca, modelo, fotografia)
				 VALUES(var_placa, var_marca, var_modelo, var_fotografia);
                 
	-- Guardamos el id que se genero --
    SET var_numeroTrailer=LAST_INSERT_ID();
END $$
DELIMITER ;

-- Stored Procedure para actualizar un trailer --
DROP PROCEDURE IF EXISTS actualizarTrailer;
DELIMITER $$
CREATE PROCEDURE actualizarTrailer( -- Recibimos los datos del trailer --
									IN	var_placa			VARCHAR(20),	-- 1
									IN	var_marca			VARCHAR(40),	-- 2
									IN	var_modelo			VARCHAR(40),	-- 3
									IN	var_fotografia		LONGTEXT,		-- 4

									IN	var_numeroTrailer	INT				-- 5
)
BEGIN
	-- Actualizamos los datos del trailer --
    UPDATE	trailer
    SET		placa=var_placa, marca=var_marca,
			modelo=var_modelo, fotografia=var_fotografia
	WHERE	numeroTrailer=var_numeroTrailer;
END $$
DELIMITER ;

-- Stored Procedure para insertar una ruta --
DROP PROCEDURE IF EXISTS insertarRuta;
DELIMITER $$
CREATE PROCEDURE insertarRuta( -- Recibimos los datos de la ruta --
								IN	var_calleOrigen				VARCHAR(129),		-- 1
								IN	var_numeroOrigen			VARCHAR(20),		-- 2
								IN	var_coloniaOrigen			VARCHAR(40),		-- 3
								IN	var_codigoPostalOrigen		VARCHAR(25),		-- 4
								IN	var_calleDestino			VARCHAR(129),		-- 5
								IN	var_numeroDestino			VARCHAR(20),		-- 6
								IN	var_coloniaDestino			VARCHAR(40),		-- 7
								IN	var_codigoPostalDestino		VARCHAR(25),		-- 8
								IN	var_numeroEmpleado			VARCHAR(15),		-- 9
								IN	var_numeroTrailer			INT,				-- 10
                                IN	var_coordenadasOrigen		VARCHAR(129),		-- 11
                                IN	var_coordenadasDestino		VARCHAR(129),		-- 12
                                IN	var_ciudadOrigen			VARCHAR(129),		-- 13
                                IN	var_estadoOrigen			VARCHAR(129),		-- 14
                                IN	var_paisOrigen				VARCHAR(129),		-- 15
                                IN	var_ciudadDestino			VARCHAR(129),		-- 16
                                IN	var_estadoDestino			VARCHAR(129),		-- 17
                                IN	var_paisDestino				VARCHAR(129),		-- 18

								OUT	var_numeroRuta				INT					-- 19
)
BEGIN
	-- Insertamos los datos de la ruta --
    INSERT INTO ruta(calleOrigen, numeroOrigen, coloniaOrigen, codigoPostalOrigen, calleDestino, numeroDestino, coloniaDestino,
						codigoPostalDestino, numeroEmpleado, numeroTrailer, coordenadasOrigen, coordenadasDestino, ciudadOrigen, 
                        estadoOrigen, paisOrigen, ciudadDestino, estadoDestino, paisDestino)
			VALUES(var_calleOrigen, var_numeroOrigen, var_coloniaOrigen, var_codigoPostalOrigen, var_calleDestino, var_numeroDestino,
					var_coloniaDestino, var_codigoPostalDestino, var_numeroEmpleado, var_numeroTrailer, var_coordenadasOrigen, 
                    var_coordenadasDestino, var_ciudadOrigen, var_estadoOrigen, var_paisOrigen, var_ciudadDestino, var_estadoDestino, var_paisDestino);
                 
	-- Guardamos el id que se genero --
    SET var_numeroRuta=LAST_INSERT_ID();
    
    -- Actualizamos el empleado que estara en ruta --
    UPDATE	empleado
    SET		enRuta=1
    WHERE	numeroEmpleado LIKE var_numeroEmpleado;
    
    -- Actualizamos el trailer que estara en ruta --
    UPDATE	trailer
    SET		enRuta=1
    WHERE	numeroTrailer=var_numeroTrailer;
END $$
DELIMITER ;

-- Stored Procedure para actualizar una ruta --
DROP PROCEDURE IF EXISTS actualizarRuta;
DELIMITER $$
CREATE PROCEDURE actualizarRuta( -- Recibimos los datos de la ruta --
								IN	var_calleOrigen				VARCHAR(129),		-- 1
								IN	var_numeroOrigen			VARCHAR(20),		-- 2
								IN	var_coloniaOrigen			VARCHAR(40),		-- 3
								IN	var_codigoPostalOrigen		VARCHAR(25),		-- 4
								IN	var_calleDestino			VARCHAR(129),		-- 5
								IN	var_numeroDestino			VARCHAR(20),		-- 6
								IN	var_coloniaDestino			VARCHAR(40),		-- 7
								IN	var_codigoPostalDestino		VARCHAR(25),		-- 8
								IN	var_numeroEmpleado			VARCHAR(15),		-- 9
								IN	var_numeroTrailer			INT,				-- 10
                                IN	var_coordenadasOrigen		VARCHAR(129),		-- 11
                                IN	var_coordenadasDestino		VARCHAR(129),		-- 12
                                IN	var_ciudadOrigen			VARCHAR(129),		-- 13
                                IN	var_estadoOrigen			VARCHAR(129),		-- 14
                                IN	var_paisOrigen				VARCHAR(129),		-- 15
                                IN	var_ciudadDestino			VARCHAR(129),		-- 16
                                IN	var_estadoDestino			VARCHAR(129),		-- 17
                                IN	var_paisDestino				VARCHAR(129),		-- 18

								IN	var_numeroRuta				INT					-- 19
)
BEGIN
	-- Primero actualizamos el empleado por si se cambio de chofer --
    UPDATE	empleado
    SET		enRuta=0
    WHERE	numeroEmpleado LIKE (SELECT numeroEmpleado FROM ruta WHERE numeroRuta=var_numeroRuta);
    
    -- Actualizamos el trailer por si se cambio de unidad --
    UPDATE	trailer
    SET		enRuta=0
    WHERE	numeroTrailer=(SELECT numeroTrailer FROM ruta WHERE numeroRuta=var_numeroRuta);
    
	-- Actualizamos los datos de la ruta --
    UPDATE	ruta
    SET		calleOrigen=var_calleOrigen, numeroOrigen=var_numeroOrigen, coloniaOrigen=var_coloniaOrigen, codigoPostalOrigen=var_codigoPostalOrigen,
			calleDestino=var_calleDestino, numeroDestino=var_numeroDestino, coloniaDestino=var_coloniaDestino, codigoPostalDestino=var_codigoPostalDestino,
			numeroEmpleado=var_numeroEmpleado, numeroTrailer=var_numeroTrailer, coordenadasOrigen=var_coordenadasOrigen, coordenadasDestino=var_coordenadasDestino, 
            ciudadOrigen=var_ciudadOrigen, estadoOrigen=var_estadoOrigen, paisOrigen=var_paisOrigen, ciudadDestino=var_ciudadDestino, 
            estadoDestino=var_estadoDestino, paisDestino=var_paisDestino
	WHERE	numeroRuta=var_numeroRuta;

    -- Actualizamos el empleado que estara en ruta --
    UPDATE	empleado
    SET		enRuta=1
    WHERE	numeroEmpleado LIKE var_numeroEmpleado;
    
    -- Actualizamos el trailer que estara en ruta --
    UPDATE	trailer
    SET		enRuta=1
    WHERE	numeroTrailer=var_numeroTrailer;
END $$
DELIMITER ;

-- Stored Procedure para reanudar una ruta por si se termino por accidente --
DROP PROCEDURE IF EXISTS reanudarRuta;
DELIMITER $$
CREATE PROCEDURE reanudarRuta( -- Recibimos los datos de la ruta --
								IN	var_numeroRuta		INT		-- 1
)
BEGIN
	-- Primero actualizamos el empleado --
    UPDATE	empleado
    SET		enRuta=1
    WHERE	numeroEmpleado LIKE (SELECT numeroEmpleado FROM ruta WHERE numeroRuta=var_numeroRuta);
    
    -- Actualizamos el trailer --
    UPDATE	trailer
    SET		enRuta=1
    WHERE	numeroTrailer=(SELECT numeroTrailer FROM ruta WHERE numeroRuta=var_numeroRuta);
    
	-- Actualizamos los datos de la ruta --
    UPDATE	ruta
    SET		estatus=1
	WHERE	numeroRuta=var_numeroRuta;
END $$
DELIMITER ;

-- Stored Procedure para terminar una ruta --
DROP PROCEDURE IF EXISTS terminarRuta;
DELIMITER $$
CREATE PROCEDURE terminarRuta( -- Recibimos los datos de la ruta --
								IN	var_numeroRuta		INT		-- 1
)
BEGIN
	-- Primero actualizamos el empleado --
    UPDATE	empleado
    SET		enRuta=0
    WHERE	numeroEmpleado LIKE (SELECT numeroEmpleado FROM ruta WHERE numeroRuta=var_numeroRuta);
    
    -- Actualizamos el trailer --
    UPDATE	trailer
    SET		enRuta=0
    WHERE	numeroTrailer=(SELECT numeroTrailer FROM ruta WHERE numeroRuta=var_numeroRuta);
    
	-- Actualizamos los datos de la ruta --
    UPDATE	ruta
    SET		estatus=0
	WHERE	numeroRuta=var_numeroRuta;
END $$
DELIMITER ;