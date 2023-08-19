-- ---------------------------------------------------------------------------- --
-- Archivo: 03_Vistas_RastreadorGPS.sql                      					--
-- Version: 1.0                                                     			--
-- Autor:   Francisco Javier Rocha Aldana   									--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx		--
-- Fecha de elaboracion: 21-06-2023                                 			--
-- ---------------------------------------------------------------------------- --

-- Seleccionamos la base de datos que usaremos --
USE rastreador_gps;

-- Vista que obtiene todos los datos de los empleados para login --
DROP VIEW IF EXISTS v_empleadosLogin;
CREATE VIEW v_empleadosLogin AS
	SELECT	e.numeroEmpleado,
			e.nombre,
			e.primerApellido,
			e.segundoApellido,
			e.curp,
			e.rfc,
			e.numeroSeguroSocial,
			DATE_FORMAT(e.fechaIngreso, '%d/%m/%Y') AS fechaIngreso,
			DATE_FORMAT(e.fechaBaja, '%d/%m/%Y') AS fechaBaja,
			e.tipoLicencia,
			e.calle,
			e.numeroExterior,
			e.colonia,
			e.ciudad,
			e.estado,
			e.telCelular,
			e.telEmergencia,
			e.fotografia,
			e.estatus,
			e.enRuta,
            
			u.idUsuario,
            u.nombreUsuario,
			u.contrasenia,
			u.lastToken,
			DATE_FORMAT(u.dateLastToken, '%d/%m/%Y %H:%i:%S') AS dateLastToken,
    
			MAX(n.idNivel) AS idNivel,
            n.nombreNivel,
            
            p.idPermiso,
			p.nombrePermiso,
			p.ruta
	FROM	empleado e
			INNER JOIN usuario u ON e.idUsuario=u.idUsuario
            INNER JOIN nivel n ON u.nombreNivel=n.nombreNivel
            INNER JOIN permiso p ON n.idPermiso=p.idPermiso
	GROUP BY	e.numeroEmpleado, idNivel;
    
    -- Vista que obtiene todos los datos de los empleados para la tabla empleados --
DROP VIEW IF EXISTS v_empleadosTabla;
CREATE VIEW v_empleadosTabla AS
	SELECT	e.numeroEmpleado,
			e.nombre,
			e.primerApellido,
			e.segundoApellido,
			e.curp,
			e.rfc,
			e.numeroSeguroSocial,
			DATE_FORMAT(e.fechaIngreso, '%d/%m/%Y') AS fechaIngreso,
			DATE_FORMAT(e.fechaBaja, '%d/%m/%Y') AS fechaBaja,
			e.tipoLicencia,
			e.calle,
			e.numeroExterior,
			e.colonia,
			e.ciudad,
			e.estado,
			e.telCelular,
			e.telEmergencia,
			e.fotografia,
			e.estatus,
			e.enRuta,
            
			u.idUsuario,
            u.nombreUsuario,
			u.contrasenia,
            n.nombreNivel,
			u.lastToken,
			DATE_FORMAT(u.dateLastToken, '%d/%m/%Y %H:%i:%S') AS dateLastToken
            
	FROM	empleado e
			INNER JOIN usuario u ON e.idUsuario=u.idUsuario
            INNER JOIN nivel n ON u.nombreNivel=n.nombreNivel;
            
-- Vista que obtiene todos los datos de la ruta --
DROP VIEW IF EXISTS v_ruta;
CREATE VIEW v_ruta AS
	SELECT	r.numeroRuta,
			r.calleOrigen,
			r.numeroOrigen,
			r.coloniaOrigen,
			r.codigoPostalOrigen,
			r.calleDestino,
			r.numeroDestino,
			r.coloniaDestino,
			r.codigoPostalDestino,
			r.estatus AS estatusRuta,
			r.numeroEmpleado,
			r.numeroTrailer,
            r.coordenadasOrigen,
            r.coordenadasDestino,
            r.ciudadOrigen,
            r.estadoOrigen,
            r.paisOrigen,
            r.ciudadDestino,
            r.estadoDestino,
            r.paisDestino,
            
            e.nombre,
			e.primerApellido,
			e.segundoApellido,
			e.curp,
			e.rfc,
			e.numeroSeguroSocial,
            DATE_FORMAT(e.fechaIngreso, '%d/%m/%Y') AS fechaIngreso,
			DATE_FORMAT(e.fechaBaja, '%d/%m/%Y') AS fechaBaja,
			e.tipoLicencia,
			e.calle,
			e.numeroExterior,
			e.colonia,
			e.ciudad,
			e.estado,
			e.telCelular,
			e.telEmergencia,
			e.fotografia AS fotoEmp,
			e.estatus AS estatusEmp,
			e.enRuta AS enRutaEmp,
			e.idUsuario,
            
            t.placa,
			t.marca,
			t.modelo,
			t.fotografia AS fotoTrailer,
			t.estatus AS estatusTra,
			t.enRuta AS enRutaTrailer
    FROM	ruta r
    INNER JOIN empleado e ON r.numeroEmpleado=e.numeroEmpleado
    INNER JOIN trailer t ON r.numeroTrailer=t.numeroTrailer;