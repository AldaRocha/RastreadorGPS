
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Permiso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rocha
 */
public class ControllerPermiso{
    public boolean actualizarPantallas(List<Permiso> p){
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=null;
        boolean respuesta=false;
        
        try{
            conn.setAutoCommit(false);
            stmt=conn.createStatement();
            for(int i=0; i<p.size(); i++){
                String sql="UPDATE permiso "
                          +"SET estatus="+p.get(i).getEstatus()+" "
                          +"WHERE idPermiso="+p.get(i).getIdPermiso();
                stmt.execute(sql);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            stmt.close();
            conn.close();
            connMySQL.close();
            respuesta=true;
        } catch(SQLException e){
            Logger.getLogger(ControllerPermiso.class.getName()).log(Level.SEVERE, null, e);
            try{
                conn.rollback();
                conn.setAutoCommit(true);
                stmt.close();
                conn.close();
                connMySQL.close();
                respuesta=false;
            } catch(SQLException ex){
                Logger.getLogger(ControllerPermiso.class.getName()).log(Level.SEVERE, null, ex);
                respuesta=false;
            }
        }
        return respuesta;
    }
    
    public List<Permiso> getAll() throws Exception{
        String sql="SELECT * FROM permiso";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareStatement(sql);
        
        ResultSet rs=prepst.executeQuery();
        
        List<Permiso> permisos=new ArrayList<>();
        
        while(rs.next())
            permisos.add(fill(rs));
        
        rs.close();
        prepst.close();
        connMySQL.close();
        
        return permisos;
    }
    
    private Permiso fill(ResultSet rs) throws SQLException{
        Permiso p=new Permiso();
        
        p.setIdPermiso(rs.getInt("idPermiso"));
        p.setNombrePermiso(rs.getString("nombrePermiso"));
        p.setRuta(rs.getString("ruta"));
        p.setEstatus(rs.getInt("estatus"));
        
        return p;
    }
}
