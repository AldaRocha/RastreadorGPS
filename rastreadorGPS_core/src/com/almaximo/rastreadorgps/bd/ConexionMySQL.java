
package com.almaximo.rastreadorgps.bd;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author rocha
 */
public class ConexionMySQL{
    Connection conn;
    
    public Connection open(){
        String user="";
        String password="";
        String url = "";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection(url, user, password);
            return conn;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public void close(){
        if(conn!=null){
            try{
                conn.close();
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Exception controlada.");
            }
        }
    }
}
