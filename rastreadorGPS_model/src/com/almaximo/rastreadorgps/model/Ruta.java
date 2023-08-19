
package com.almaximo.rastreadorgps.model;

/**
 *
 * @author rocha
 */
public class Ruta{
    private int numeroRuta;
    private String calleOrigen;
    private String numeroOrigen;
    private String coloniaOrigen;
    private String codigoPostalOrigen;
    private String ciudadOrigen;
    private String estadoOrigen;
    private String paisOrigen;
    private String coordenadasOrigen;
    private String calleDestino;
    private String numeroDestino;
    private String coloniaDestino;
    private String codigoPostalDestino;
    private String ciudadDestino;
    private String estadoDestino;
    private String paisDestino;
    private String coordenadasDestino;
    private int estatus;
    private Empleado empleado;
    private Trailer trailer;

    public Ruta(int numeroRuta, String calleOrigen, String numeroOrigen, String coloniaOrigen, String codigoPostalOrigen, String ciudadOrigen, String estadoOrigen, String paisOrigen, String coordenadasOrigen, String calleDestino, String numeroDestino, String coloniaDestino, String codigoPostalDestino, String ciudadDestino, String estadoDestino, String paisDestino, String coordenadasDestino, int estatus, Empleado empleado, Trailer trailer){
        this.numeroRuta=numeroRuta;
        this.calleOrigen=calleOrigen;
        this.numeroOrigen=numeroOrigen;
        this.coloniaOrigen=coloniaOrigen;
        this.codigoPostalOrigen=codigoPostalOrigen;
        this.ciudadOrigen=ciudadOrigen;
        this.estadoOrigen=estadoOrigen;
        this.paisOrigen=paisOrigen;
        this.coordenadasOrigen=coordenadasOrigen;
        this.calleDestino=calleDestino;
        this.numeroDestino=numeroDestino;
        this.coloniaDestino=coloniaDestino;
        this.codigoPostalDestino=codigoPostalDestino;
        this.ciudadDestino=ciudadDestino;
        this.estadoDestino=estadoDestino;
        this.paisDestino=paisDestino;
        this.coordenadasDestino=coordenadasDestino;
        this.estatus=estatus;
        this.empleado=empleado;
        this.trailer=trailer;
    }

    public Ruta(){
    }

    public String getCiudadOrigen(){
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen){
        this.ciudadOrigen=ciudadOrigen;
    }

    public String getEstadoOrigen(){
        return estadoOrigen;
    }

    public void setEstadoOrigen(String estadoOrigen){
        this.estadoOrigen=estadoOrigen;
    }

    public String getPaisOrigen(){
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen){
        this.paisOrigen=paisOrigen;
    }

    public String getCiudadDestino(){
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino){
        this.ciudadDestino=ciudadDestino;
    }

    public String getEstadoDestino(){
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino){
        this.estadoDestino=estadoDestino;
    }

    public String getPaisDestino(){
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino){
        this.paisDestino=paisDestino;
    }

    public int getNumeroRuta(){
        return numeroRuta;
    }

    public void setNumeroRuta(int numeroRuta){
        this.numeroRuta=numeroRuta;
    }

    public String getCalleOrigen(){
        return calleOrigen;
    }

    public void setCalleOrigen(String calleOrigen){
        this.calleOrigen=calleOrigen;
    }

    public String getNumeroOrigen(){
        return numeroOrigen;
    }

    public void setNumeroOrigen(String numeroOrigen){
        this.numeroOrigen=numeroOrigen;
    }

    public String getColoniaOrigen(){
        return coloniaOrigen;
    }

    public void setColoniaOrigen(String coloniaOrigen){
        this.coloniaOrigen=coloniaOrigen;
    }

    public String getCodigoPostalOrigen(){
        return codigoPostalOrigen;
    }

    public void setCodigoPostalOrigen(String codigoPostalOrigen){
        this.codigoPostalOrigen=codigoPostalOrigen;
    }

    public String getCoordenadasOrigen(){
        return coordenadasOrigen;
    }

    public void setCoordenadasOrigen(String coordenadasOrigen){
        this.coordenadasOrigen=coordenadasOrigen;
    }

    public String getCalleDestino(){
        return calleDestino;
    }

    public void setCalleDestino(String calleDestino){
        this.calleDestino=calleDestino;
    }

    public String getNumeroDestino(){
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino){
        this.numeroDestino=numeroDestino;
    }

    public String getColoniaDestino(){
        return coloniaDestino;
    }

    public void setColoniaDestino(String coloniaDestino){
        this.coloniaDestino=coloniaDestino;
    }

    public String getCodigoPostalDestino(){
        return codigoPostalDestino;
    }

    public void setCodigoPostalDestino(String codigoPostalDestino){
        this.codigoPostalDestino=codigoPostalDestino;
    }

    public String getCoordenadasDestino(){
        return coordenadasDestino;
    }

    public void setCoordenadasDestino(String coordenadasDestino){
        this.coordenadasDestino=coordenadasDestino;
    }

    public int getEstatus(){
        return estatus;
    }

    public void setEstatus(int estatus){
        this.estatus=estatus;
    }

    public Empleado getEmpleado(){
        return empleado;
    }

    public void setEmpleado(Empleado empleado){
        this.empleado=empleado;
    }

    public Trailer getTrailer(){
        return trailer;
    }

    public void setTrailer(Trailer trailer){
        this.trailer=trailer;
    }
}
