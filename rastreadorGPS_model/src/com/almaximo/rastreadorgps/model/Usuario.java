
package com.almaximo.rastreadorgps.model;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author rocha
 */
public class Usuario{
    private int idUsuario;
    private String nombreUsuario;
    private String contrasenia;
    private String lastToken;
    private String dateLastToken;
    private Nivel nivel;

    public Usuario(int idUsuario, String nombreUsuario, String contrasenia, String lastToken, String dateLastToken, Nivel nivel){
        this.idUsuario=idUsuario;
        this.nombreUsuario=nombreUsuario;
        this.contrasenia=contrasenia;
        this.lastToken=lastToken;
        this.dateLastToken=dateLastToken;
        this.nivel=nivel;
    }

    public Usuario(){
    }

    public Nivel getNivel(){
        return nivel;
    }

    public void setNivel(Nivel nivel){
        this.nivel=nivel;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario=idUsuario;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario=nombreUsuario;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia=contrasenia;
    }

    public String getLastToken(){
        return lastToken;
    }

    public void setLastToken(String lastToken){
        this.lastToken=lastToken;
    }

    public String getDateLastToken(){
        return dateLastToken;
    }

    public void setDateLastToken(String dateLastToken){
        this.dateLastToken=dateLastToken;
    }

    public void setLastToken(){
        String u=this.getNombreUsuario();
        String p=this.getContrasenia();
        String k=new Date().toString();
        String x=(DigestUtils.sha256Hex(u+";"+p+";"+k));
        this.lastToken=x;
    }
    
    public void setDateLastToken(){
        String fecha=new Date().toString();
        this.dateLastToken=fecha;
    }
}
