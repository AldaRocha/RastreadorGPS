
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Clave;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author rocha
 */
public class ControllerClave{
    public Clave getClave(String nombreClave) throws Exception{
        String sql="SELECT * FROM clave WHERE nombreClave LIKE '"+nombreClave+"'";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=(Statement)conn.createStatement();
        
        ResultSet rs=stmt.executeQuery(sql);
        
        Clave c=new Clave();
        
        if(rs.next())
            c=(fill(rs));
        
        rs.close();
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return c;
    }
    
    private Clave fill(ResultSet rs) throws Exception{
        Clave c=new Clave();
        
        c.setIdClave(rs.getInt("idClave"));
        c.setNombreClave(rs.getString("nombreClave"));
        c.setDetalleClave(rs.getString("detalleClave"));
        c.setClave(rs.getString("clave"));
        
        return c;
    }
}
