
package com.almaximo.rastreadorgps.model;

/**
 *
 * @author rocha
 */
public class Nivel{
    private int idNivel;
    private String nombreNivel;
    private Permiso permiso;

    public Nivel(int idNivel, String nombreNivel, Permiso permiso){
        this.idNivel=idNivel;
        this.nombreNivel=nombreNivel;
        this.permiso=permiso;
    }

    public Nivel(){
    }

    public Permiso getPermiso(){
        return permiso;
    }

    public void setPermiso(Permiso permiso){
        this.permiso=permiso;
    }

    public int getIdNivel(){
        return idNivel;
    }

    public void setIdNivel(int idNivel){
        this.idNivel=idNivel;
    }

    public String getNombreNivel(){
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel){
        this.nombreNivel=nombreNivel;
    }
}
