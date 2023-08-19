
package com.almaximo.rastreadorgps.model;

/**
 *
 * @author rocha
 */
public class Permiso{
    private int idPermiso;
    private String nombrePermiso;
    private String ruta;
    private int estatus;

    public Permiso(int idPermiso, String nombrePermiso, String ruta, int estatus){
        this.idPermiso=idPermiso;
        this.nombrePermiso=nombrePermiso;
        this.ruta=ruta;
        this.estatus=estatus;
    }

    public Permiso(){
    }

    public int getIdPermiso(){
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso){
        this.idPermiso=idPermiso;
    }

    public String getNombrePermiso(){
        return nombrePermiso;
    }

    public void setNombrePermiso(String nombrePermiso){
        this.nombrePermiso=nombrePermiso;
    }

    public String getRuta(){
        return ruta;
    }

    public void setRuta(String ruta){
        this.ruta=ruta;
    }

    public int getEstatus(){
        return estatus;
    }

    public void setEstatus(int estatus){
        this.estatus=estatus;
    }
}
