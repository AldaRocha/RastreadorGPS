
package com.almaximo.rastreadorgps.model;

/**
 *
 * @author rocha
 */
public class Clave{
    private int idClave;
    private String nombreClave;
    private String detalleClave;
    private String clave;

    public Clave(int idClave, String nombreClave, String detalleClave, String clave){
        this.idClave=idClave;
        this.nombreClave=nombreClave;
        this.detalleClave=detalleClave;
        this.clave=clave;
    }

    public Clave(){
    }

    public int getIdClave(){
        return idClave;
    }

    public void setIdClave(int idClave){
        this.idClave=idClave;
    }

    public String getNombreClave(){
        return nombreClave;
    }

    public void setNombreClave(String nombreClave){
        this.nombreClave=nombreClave;
    }

    public String getDetalleClave(){
        return detalleClave;
    }

    public void setDetalleClave(String detalleClave){
        this.detalleClave=detalleClave;
    }

    public String getClave(){
        return clave;
    }

    public void setClave(String clave){
        this.clave=clave;
    }
}
