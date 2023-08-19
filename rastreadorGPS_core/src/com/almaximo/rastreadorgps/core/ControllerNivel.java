
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Nivel;
import com.almaximo.rastreadorgps.model.Permiso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rocha
 */
public class ControllerNivel{
    public List<Nivel> accesos(String nivel) throws Exception{
        String sql="SELECT n.*, p.nombrePermiso, p.ruta, p.estatus FROM nivel n INNER JOIN permiso p ON n.idPermiso=p.idPermiso WHERE n.nombreNivel LIKE ?";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareStatement(sql);
        
        ResultSet rs=null;
        
        prepst.setString(1, nivel);
        
        rs=prepst.executeQuery();
        
        List<Nivel> n=new ArrayList<>();
        
        while(rs.next())
            n.add(fill(rs));
        
        rs.close();
        prepst.close();
        connMySQL.close();
        
        return n;
    }
    
    private Nivel fill(ResultSet rs) throws Exception{
        Nivel n=new Nivel();
        Permiso p=new Permiso();
        
        p.setIdPermiso(rs.getInt("idPermiso"));
        p.setNombrePermiso(rs.getString("nombrePermiso"));
        p.setRuta(rs.getString("ruta"));
        p.setEstatus(rs.getInt("estatus"));
        
        n.setIdNivel(rs.getInt("idNivel"));
        n.setNombreNivel(rs.getString("nombreNivel"));
        n.setPermiso(p);
        
        return n;
    }
    
    public List<Nivel> tiposNivel(String nivel) throws Exception{
        List<Nivel> n=new ArrayList<>();
        if(nivel.equals("Super Administrador")){
            String sql="SELECT nombreNivel FROM nivel GROUP BY nombreNivel";
        
            ConexionMySQL connMySQL=new ConexionMySQL();
        
            Connection conn=connMySQL.open();
        
            Statement stmt=(Statement)conn.createStatement();
        
            ResultSet rs=stmt.executeQuery(sql);
        
            while(rs.next())
                n.add(fillN(rs));
            
            rs.close();
            stmt.close();
            conn.close();
            connMySQL.close();
        } else{
            String sql="SELECT nombreNivel FROM nivel WHERE nombreNivel NOT LIKE 'Super Administrador' GROUP BY nombreNivel";
            
            ConexionMySQL connMySQL=new ConexionMySQL();
            
            Connection conn=connMySQL.open();
            
            Statement stmt=(Statement)conn.createStatement();
            
            ResultSet rs=stmt.executeQuery(sql);
            
            while(rs.next())
                n.add(fillN(rs));
            
            rs.close();
            stmt.close();
            conn.close();
            connMySQL.close();
        }
        return n;
    }
    
    private Nivel fillN(ResultSet rs) throws Exception{
        Nivel n=new Nivel();
        
        n.setNombreNivel(rs.getString("nombreNivel"));        
        
        return n;
    }
}
