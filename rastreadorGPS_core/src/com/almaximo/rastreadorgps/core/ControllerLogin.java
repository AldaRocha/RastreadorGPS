
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Nivel;
import com.almaximo.rastreadorgps.model.Permiso;
import com.almaximo.rastreadorgps.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rocha
 */
public class ControllerLogin{
    public Empleado login(String usuario, String contrasenia) throws Exception{
        String sql="SELECT * FROM v_empleadosLogin ve WHERE ve.nombreUsuario=? AND ve.contrasenia=?";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareStatement(sql);
        
        ResultSet rs=null;
        
        prepst.setString(1, usuario);
        prepst.setString(2, contrasenia);
        
        rs=prepst.executeQuery();
        
        Empleado e=null;
        
        if(rs.next())
            e=(fill(rs));
        
        rs.close();
        prepst.close();
        connMySQL.close();
        
        return e;
    }
    
    private Empleado fill(ResultSet rs)throws Exception{
        Empleado e=new Empleado();
        Usuario u=new Usuario();
        Nivel n=new Nivel();
        Permiso p=new Permiso();
        
        p.setIdPermiso(rs.getInt("idPermiso"));
        p.setNombrePermiso(rs.getString("nombrePermiso"));
        p.setRuta(rs.getString("ruta"));
        p.setEstatus(rs.getInt("estatus"));
        
        n.setIdNivel(rs.getInt("idNivel"));
        n.setNombreNivel(rs.getString("nombreNivel"));
        n.setPermiso(p);
        
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setLastToken(rs.getString("lastToken"));
        u.setDateLastToken(rs.getString("dateLastToken"));
        u.setNivel(n);
        
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
        e.setFotografia(rs.getString("fotografia"));
        e.setEstatus(rs.getInt("estatus"));
        e.setEnRuta(rs.getInt("enRuta"));
        e.setUsuario(u);
        
        return e;
    }
    
    public void generarToken(int idUsuario, String token) throws SQLException{       
        String sql="CALL generarToken(?, ?)";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setInt(1, idUsuario);
        clblsmt.setString(2, token);
        
        clblsmt.executeUpdate();
        
        clblsmt.close();
        connMySQL.close();
    }
    
    public boolean validarToken(String token) throws Exception{
        boolean r=false;
        String sql="SELECT * FROM v_empleadosLogin WHERE lastToken='"+token+"'";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=(Statement)conn.createStatement();
        
        ResultSet rs=stmt.executeQuery(sql);
        
        if(rs.next())
            r=true;
        
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return r;
    }
    
    public boolean eliminarToken(Empleado e) throws Exception{
        boolean r=false;
        String sql="UPDATE usuario SET lastToken='' WHERE idUsuario=?";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareCall(sql);
        
        prepst.setInt(1, e.getUsuario().getIdUsuario());
        prepst.execute();
        r=true;
        
        prepst.close();
        conn.close();
        connMySQL.close();
        
        return r;
    }
}
