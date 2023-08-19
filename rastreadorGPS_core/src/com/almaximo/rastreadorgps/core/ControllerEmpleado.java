
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Nivel;
import com.almaximo.rastreadorgps.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rocha
 */
public class ControllerEmpleado{
    public String insertarEmpleado(Empleado e) throws Exception{
        String sql="{CALL insertarEmpleado(?, ?, ?, "+
                                          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                                          "?, ?)}";
        
        int idUsuarioGenerado=-1;
        String numeroEmpleadoGenerado="";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, e.getUsuario().getNombreUsuario());
        clblsmt.setString(2, e.getUsuario().getContrasenia());
        clblsmt.setString(3, e.getUsuario().getNivel().getNombreNivel());
        
        clblsmt.setString(4, e.getNombre());
        clblsmt.setString(5, e.getPrimerApellido());
        clblsmt.setString(6, e.getSegundoApellido());
        clblsmt.setString(7, e.getCurp());
        clblsmt.setString(8, e.getRfc());
        clblsmt.setLong(9, e.getNumeroSeguroSocial());
        clblsmt.setString(10, e.getFechaIngreso());
        clblsmt.setString(11, e.getTipoLicencia());
        clblsmt.setString(12, e.getCalle());
        clblsmt.setString(13, e.getNumeroExterior());
        clblsmt.setString(14, e.getColonia());
        clblsmt.setString(15, e.getCiudad());
        clblsmt.setString(16, e.getEstado());
        clblsmt.setString(17, e.getTelCelular());
        clblsmt.setString(18, e.getTelEmergencia());
        clblsmt.setString(19, e.getFotografia());
        
        clblsmt.registerOutParameter(20, Types.INTEGER);
        clblsmt.registerOutParameter(21, Types.VARCHAR);
        
        clblsmt.executeUpdate();
        
        idUsuarioGenerado=clblsmt.getInt(20);
        numeroEmpleadoGenerado=clblsmt.getString(21);
        
        e.getUsuario().setIdUsuario(idUsuarioGenerado);
        e.setNumeroEmpleado(numeroEmpleadoGenerado);
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
        
        return numeroEmpleadoGenerado;
    }
    
    public void actualizarEmpleado(Empleado e) throws Exception{
        String sql="{CALL actualizarEmpleado(?, ?, ?, "+
                                            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
                                            "?, ?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, e.getUsuario().getNombreUsuario());
        clblsmt.setString(2, e.getUsuario().getContrasenia());
        clblsmt.setString(3, e.getUsuario().getNivel().getNombreNivel());
        clblsmt.setString(4, e.getNombre());
        clblsmt.setString(5, e.getPrimerApellido());
        clblsmt.setString(6, e.getSegundoApellido());
        clblsmt.setString(7, e.getCurp());
        clblsmt.setString(8, e.getRfc());
        clblsmt.setLong(9, e.getNumeroSeguroSocial());
        clblsmt.setString(10, e.getFechaIngreso());
        clblsmt.setString(11, e.getTipoLicencia());
        clblsmt.setString(12, e.getCalle());
        clblsmt.setString(13, e.getNumeroExterior());
        clblsmt.setString(14, e.getColonia());
        clblsmt.setString(15, e.getCiudad());
        clblsmt.setString(16, e.getEstado());
        clblsmt.setString(17, e.getTelCelular());
        clblsmt.setString(18, e.getTelEmergencia());
        clblsmt.setString(19, e.getFotografia());
        clblsmt.setInt(20, e.getUsuario().getIdUsuario());
        clblsmt.setString(21, e.getNumeroEmpleado());
        
        clblsmt.executeUpdate();
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public List<Empleado> getAllEmpleado(String nivel) throws Exception{
        List<Empleado> e=new ArrayList<>();
        if(nivel.equals("Super Administrador")){
            String sqlSA="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Super Administrador' GROUP BY numeroEmpleado";
            String sqlAD="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Administrador' GROUP BY numeroEmpleado";
            String sqlAY="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Ayudante' GROUP BY numeroEmpleado";
            String sqlMO="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Monitorista' GROUP BY numeroEmpleado";
            
            ConexionMySQL connMySQL=new ConexionMySQL();
            
            Connection conn=connMySQL.open();
            
            Statement stmt=(Statement)conn.createStatement();
                    
            ResultSet rsSA=stmt.executeQuery(sqlSA);                                 
            while(rsSA.next())
                e.add(fill(rsSA));
            
            ResultSet rsAD=stmt.executeQuery(sqlAD);            
            while(rsAD.next())
                e.add(fill(rsAD));
            
            ResultSet rsAY=stmt.executeQuery(sqlAY);            
            while(rsAY.next())
                e.add(fill(rsAY));
            
            ResultSet rsMO=stmt.executeQuery(sqlMO);
            while(rsMO.next())
                e.add(fill(rsMO));
            
            rsSA.close();
            rsAD.close();
            rsAY.close();
            rsMO.close();
            stmt.close();
            conn.close();
            connMySQL.close();                        
        } else if(nivel.equals("Administrador")){
            String sqlAD="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Administrador' GROUP BY numeroEmpleado";
            String sqlAY="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Ayudante' GROUP BY numeroEmpleado";
            String sqlMO="SELECT * FROM v_empleadosTabla WHERE nombreNivel LIKE 'Monitorista' GROUP BY numeroEmpleado";
            
            ConexionMySQL connMySQL=new ConexionMySQL();
            
            Connection conn=connMySQL.open();
            
            Statement stmt=(Statement)conn.createStatement();                    
            
            ResultSet rsAD=stmt.executeQuery(sqlAD);
            while(rsAD.next())
                e.add(fill(rsAD));
            
            ResultSet rsAY=stmt.executeQuery(sqlAY);
            while(rsAY.next())
                e.add(fill(rsAY));
            
            ResultSet rsMO=stmt.executeQuery(sqlMO);
            while(rsMO.next())
                e.add(fill(rsMO));
            
            rsAD.close();
            rsAY.close();
            rsMO.close();
            stmt.close();
            conn.close();
            connMySQL.close();                        
        }
        return e;
    }
    
    private Empleado fill(ResultSet rs) throws Exception{
        Empleado e=new Empleado();
        Usuario u=new Usuario();
        Nivel n=new Nivel();
        
        n.setNombreNivel(rs.getString("nombreNivel"));
        
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
    
    public boolean activarEmpleado(Empleado e) throws Exception{
        boolean respuesta=false;
        String sql="UPDATE empleado SET estatus=1 WHERE numeroEmpleado LIKE ?";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareCall(sql);
        
        prepst.setString(1, e.getNumeroEmpleado());
        prepst.execute();
        respuesta=true;
        
        prepst.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
    
    public boolean eliminarEmpleado(Empleado e) throws Exception{
        boolean respuesta=false;
        String sql="{CALL eliminarEmpleado(?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, e.getNumeroEmpleado());
        clblsmt.execute();
        respuesta=true;
        
        clblsmt.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
    
    public boolean cambiarContraseña(Empleado e) throws Exception{
        boolean respuesta=false;
        String sql="{CALL cambiarContraseña(?, ?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setInt(1, e.getUsuario().getIdUsuario());
        clblsmt.setString(2, e.getUsuario().getContrasenia());
        clblsmt.execute();
        respuesta=true;
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
        
        return respuesta;
    }
    
    public boolean duplicadoEmpleado(Empleado e) throws Exception{
        boolean respuesta=false;
        String sql="SELECT * FROM v_empleadosTabla WHERE curp LIKE '"+e.getCurp()+"' AND numeroSeguroSocial="+e.getNumeroSeguroSocial();
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=(Statement)conn.createStatement();
        
        ResultSet rs=stmt.executeQuery(sql);
        
        if(rs.next())
            respuesta=true;
        
        stmt.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
    
    public boolean duplicadoUsuario(Empleado e) throws Exception{
        boolean respuesta=false;
        String sql="SELECT * FROM v_empleadosTabla TE WHERE TE.nombreUsuario LIKE '"+e.getUsuario().getNombreUsuario()+"'";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=(Statement)conn.createStatement();
        
        ResultSet rs=stmt.executeQuery(sql);
        
        if(rs.next())
            respuesta=true;
        
        stmt.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
}
