
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Ruta;
import com.almaximo.rastreadorgps.model.Trailer;
import com.almaximo.rastreadorgps.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rocha
 */
public class ControllerRuta{
    public int insertarRuta(Ruta r) throws Exception{
        String sql="{CALL insertarRuta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        int numeroRutaGenerado=-1;
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, r.getCalleOrigen());
        clblsmt.setString(2, r.getNumeroOrigen());
        clblsmt.setString(3, r.getColoniaOrigen());
        clblsmt.setString(4, r.getCodigoPostalOrigen());
        clblsmt.setString(5, r.getCalleDestino());
        clblsmt.setString(6, r.getNumeroDestino());
        clblsmt.setString(7, r.getColoniaDestino());
        clblsmt.setString(8, r.getCodigoPostalDestino());
        clblsmt.setString(9, r.getEmpleado().getNumeroEmpleado());
        clblsmt.setInt(10, r.getTrailer().getNumeroTrailer());
        clblsmt.setString(11, r.getCoordenadasOrigen());
        clblsmt.setString(12, r.getCoordenadasDestino());
        clblsmt.setString(13, r.getCiudadOrigen());
        clblsmt.setString(14, r.getEstadoOrigen());
        clblsmt.setString(15, r.getPaisOrigen());
        clblsmt.setString(16, r.getCiudadDestino());
        clblsmt.setString(17, r.getEstadoDestino());
        clblsmt.setString(18, r.getPaisDestino());

        clblsmt.registerOutParameter(19, Types.INTEGER);
        
        clblsmt.executeUpdate();

        numeroRutaGenerado=clblsmt.getInt(19);
        
        r.setNumeroRuta(numeroRutaGenerado);
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
        
        return numeroRutaGenerado;
    }
    
    public void actualizarRuta(Ruta r) throws Exception{
        String sql="{CALL actualizarRuta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, r.getCalleOrigen());
        clblsmt.setString(2, r.getNumeroOrigen());
        clblsmt.setString(3, r.getColoniaOrigen());
        clblsmt.setString(4, r.getCodigoPostalOrigen());
        clblsmt.setString(5, r.getCalleDestino());
        clblsmt.setString(6, r.getNumeroDestino());
        clblsmt.setString(7, r.getColoniaDestino());
        clblsmt.setString(8, r.getCodigoPostalDestino());
        clblsmt.setString(9, r.getEmpleado().getNumeroEmpleado());
        clblsmt.setInt(10, r.getTrailer().getNumeroTrailer());
        clblsmt.setString(11, r.getCoordenadasOrigen());
        clblsmt.setString(12, r.getCoordenadasDestino());
        clblsmt.setString(13, r.getCiudadOrigen());
        clblsmt.setString(14, r.getEstadoOrigen());
        clblsmt.setString(15, r.getPaisOrigen());
        clblsmt.setString(16, r.getCiudadDestino());
        clblsmt.setString(17, r.getEstadoDestino());
        clblsmt.setString(18, r.getPaisDestino());
        
        clblsmt.setInt(19, r.getNumeroRuta());
        
        clblsmt.executeUpdate();
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public List<Ruta> getAll() throws Exception{
        String sql="SELECT * FROM v_ruta";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=(Statement)conn.createStatement();
        
        ResultSet rs=stmt.executeQuery(sql);
        
        List<Ruta> r=new ArrayList<>();
        
        while(rs.next()){
            r.add(fill(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return r;
    }
    
    private Ruta fill(ResultSet rs) throws Exception{
        Ruta r=new Ruta();
        Usuario u=new Usuario();
        Empleado e=new Empleado();
        Trailer t=new Trailer();

        t.setNumeroTrailer(rs.getInt("numeroTrailer"));
        t.setPlaca(rs.getString("placa"));
        t.setMarca(rs.getString("marca"));
        t.setModelo(rs.getString("modelo"));
        t.setFotografia(rs.getString("fotoTrailer"));
        t.setEstatus(rs.getInt("estatusTra"));
        t.setEnRuta(rs.getInt("enRutaTrailer"));
        
        u.setIdUsuario(rs.getInt("idUsuario"));
        
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));
        e.setNombre(rs.getString("nombre"));
        e.setPrimerApellido(rs.getString("primerApellido"));
        e.setSegundoApellido(rs.getString("segundoApellido"));
        e.setCurp(rs.getString("curp"));
        e.setRfc(rs.getString("rfc"));
        e.setNumeroSeguroSocial(rs.getLong("numeroSeguroSocial"));
        e.setFechaIngreso(rs.getString("fechaIngreso"));
        e.setFechaBaja(rs.getString("fechaBaja"));
        e.setTipoLicencia(rs.getString("tipoLicencia"));
        e.setCalle(rs.getString("calle"));
        e.setNumeroExterior(rs.getString("numeroExterior"));
        e.setColonia(rs.getString("colonia"));
        e.setCiudad(rs.getString("ciudad"));
        e.setEstado(rs.getString("estado"));
        e.setTelCelular(rs.getString("telCelular"));
        e.setTelEmergencia(rs.getString("telEmergencia"));
        e.setFotografia(rs.getString("fotoEmp"));
        e.setEstatus(rs.getInt("estatusEmp"));
        e.setEnRuta(rs.getInt("enRutaEmp"));
        e.setUsuario(u);
        
        r.setNumeroRuta(rs.getInt("numeroRuta"));
        r.setCalleOrigen(rs.getString("calleOrigen"));
        r.setNumeroOrigen(rs.getString("numeroOrigen"));
        r.setColoniaOrigen(rs.getString("coloniaOrigen"));
        r.setCodigoPostalOrigen(rs.getString("codigoPostalOrigen"));
        r.setCiudadOrigen(rs.getString("ciudadOrigen"));
        r.setEstadoOrigen(rs.getString("estadoOrigen"));
        r.setPaisOrigen(rs.getString("paisOrigen"));
        r.setCoordenadasOrigen(rs.getString("coordenadasOrigen"));
        r.setCalleDestino(rs.getString("calleDestino"));
        r.setNumeroDestino(rs.getString("numeroDestino"));
        r.setColoniaDestino(rs.getString("coloniaDestino"));
        r.setCodigoPostalDestino(rs.getString("codigoPostalDestino"));
        r.setCiudadDestino(rs.getString("ciudadDestino"));
        r.setEstadoDestino(rs.getString("estadoDestino"));
        r.setPaisDestino(rs.getString("paisDestino"));
        r.setCoordenadasDestino(rs.getString("coordenadasDestino"));
        r.setEstatus(rs.getInt("estatusRuta"));
        r.setEmpleado(e);
        r.setTrailer(t);
        
        return r;
    }
    
    public boolean reanudarRuta(Ruta r) throws Exception{
        boolean respuesta=false;
        String sql="{CALL reanudarRuta(?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setInt(1, r.getNumeroRuta());
        clblsmt.execute();
        respuesta=true;
        
        clblsmt.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
    
    public boolean terminarRuta(Ruta r) throws Exception{
        boolean respuesta=false;
        String sql="{CALL terminarRuta(?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setInt(1, r.getNumeroRuta());
        clblsmt.execute();
        respuesta=true;
        
        clblsmt.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
}
