
package com.almaximo.rastreadorgps.core;

import com.almaximo.rastreadorgps.bd.ConexionMySQL;
import com.almaximo.rastreadorgps.model.Trailer;
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
public class ControllerTrailer{
    public int insertarTrailer(Trailer t) throws Exception{
        String sql="{CALL insertarTrailer(?, ?, ?, ?, ?)}";
        
        int idNumeroTrailerGenerado=-1;
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, t.getPlaca());
        clblsmt.setString(2, t.getMarca());
        clblsmt.setString(3, t.getModelo());
        clblsmt.setString(4, t.getFotografia());

        clblsmt.registerOutParameter(5, Types.INTEGER);
        
        clblsmt.executeUpdate();
        
        idNumeroTrailerGenerado=clblsmt.getInt(5);
        
        t.setNumeroTrailer(idNumeroTrailerGenerado);
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
        
        return idNumeroTrailerGenerado;
    }
    
    public void actualizarTrailer(Trailer t) throws Exception{
        String sql="{CALL actualizarTrailer(?, ?, ?, ?, ?)}";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        CallableStatement clblsmt=conn.prepareCall(sql);
        
        clblsmt.setString(1, t.getPlaca());
        clblsmt.setString(2, t.getMarca());
        clblsmt.setString(3, t.getModelo());
        clblsmt.setString(4, t.getFotografia());

        clblsmt.setInt(5, t.getNumeroTrailer());
        
        clblsmt.executeUpdate();
        
        clblsmt.close();
        conn.close();
        connMySQL.close();
    }
    
    public List<Trailer> getAll() throws Exception{
        String sql="SELECT * FROM trailer";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        Statement stmt=(Statement)conn.createStatement();
        
        ResultSet rs=stmt.executeQuery(sql);
        
        List<Trailer> t=new ArrayList<>();
        
        while(rs.next()){
            t.add(fill(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return t;
    }
    
    private Trailer fill(ResultSet rs) throws Exception{
        Trailer t=new Trailer();

        t.setNumeroTrailer(rs.getInt("numeroTrailer"));
        t.setPlaca(rs.getString("placa"));
        t.setMarca(rs.getString("marca"));
        t.setModelo(rs.getString("modelo"));
        t.setFotografia(rs.getString("fotografia"));
        t.setEstatus(rs.getInt("estatus"));
        t.setEnRuta(rs.getInt("enRuta"));
        
        return t;
    }
    
    public boolean activarTrailer(Trailer t) throws Exception{
        boolean respuesta=false;
        String sql="UPDATE trailer SET estatus=1 WHERE numeroTrailer=?";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareCall(sql);
        
        prepst.setInt(1, t.getNumeroTrailer());
        prepst.execute();
        respuesta=true;
        
        prepst.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }

    public boolean eliminarTrailer(Trailer t)throws Exception{
        boolean respuesta=false;
        String sql="UPDATE trailer SET estatus=0 WHERE numeroTrailer=?";
        
        ConexionMySQL connMySQL=new ConexionMySQL();
        
        Connection conn=connMySQL.open();
        
        PreparedStatement prepst=conn.prepareCall(sql);
        
        prepst.setInt(1, t.getNumeroTrailer());
        prepst.execute();
        respuesta=true;
        
        prepst.close();
        conn.close();
        connMySQL.close();

        return respuesta;
    }
    
    public boolean duplicado(Trailer t) throws Exception{
        boolean respuesta=false;
        String sql="SELECT * FROM trailer WHERE placa LIKE '"+t.getPlaca()+"'";
        
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
