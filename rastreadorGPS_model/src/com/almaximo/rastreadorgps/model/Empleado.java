
package com.almaximo.rastreadorgps.model;

/**
 *
 * @author rocha
 */
public class Empleado{
    private String numeroEmpleado;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String curp;
    private String rfc;
    private long numeroSeguroSocial;
    private String fechaIngreso;
    private String fechaBaja;
    private String tipoLicencia;
    private String calle;
    private String numeroExterior;
    private String colonia;
    private String ciudad;
    private String estado;
    private String telCelular;
    private String telEmergencia;
    private String fotografia;
    private int estatus;
    private int enRuta;
    private Usuario usuario;

    public Empleado(String numeroEmpleado, String nombre, String primerApellido, String segundoApellido, String curp, String rfc, long numeroSeguroSocial, String fechaIngreso, String fechaBaja, String tipoLicencia, String calle, String numeroExterior, String colonia, String ciudad, String estado, String telCelular, String telEmergencia, String fotografia, int estatus, int enRuta, Usuario usuario){
        this.numeroEmpleado=numeroEmpleado;
        this.nombre=nombre;
        this.primerApellido=primerApellido;
        this.segundoApellido=segundoApellido;
        this.curp=curp;
        this.rfc=rfc;
        this.numeroSeguroSocial=numeroSeguroSocial;
        this.fechaIngreso=fechaIngreso;
        this.fechaBaja=fechaBaja;
        this.tipoLicencia=tipoLicencia;
        this.calle=calle;
        this.numeroExterior=numeroExterior;
        this.colonia=colonia;
        this.ciudad=ciudad;
        this.estado=estado;
        this.telCelular=telCelular;
        this.telEmergencia=telEmergencia;
        this.fotografia=fotografia;
        this.estatus=estatus;
        this.enRuta=enRuta;
        this.usuario=usuario;
    }

    public Empleado(){
    }

    public String getNumeroEmpleado(){
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado){
        this.numeroEmpleado=numeroEmpleado;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getPrimerApellido(){
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido){
        this.primerApellido=primerApellido;
    }

    public String getSegundoApellido(){
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido){
        this.segundoApellido=segundoApellido;
    }

    public String getCurp(){
        return curp;
    }

    public void setCurp(String curp){
        this.curp=curp;
    }

    public String getRfc(){
        return rfc;
    }

    public void setRfc(String rfc){
        this.rfc=rfc;
    }

    public long getNumeroSeguroSocial(){
        return numeroSeguroSocial;
    }

    public void setNumeroSeguroSocial(long numeroSeguroSocial){
        this.numeroSeguroSocial=numeroSeguroSocial;
    }

    public String getFechaIngreso(){
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso){
        this.fechaIngreso=fechaIngreso;
    }

    public String getFechaBaja(){
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja){
        this.fechaBaja=fechaBaja;
    }

    public String getTipoLicencia(){
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia){
        this.tipoLicencia=tipoLicencia;
    }

    public String getCalle(){
        return calle;
    }

    public void setCalle(String calle){
        this.calle=calle;
    }

    public String getNumeroExterior(){
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior){
        this.numeroExterior=numeroExterior;
    }

    public String getColonia(){
        return colonia;
    }

    public void setColonia(String colonia){
        this.colonia=colonia;
    }

    public String getCiudad(){
        return ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad=ciudad;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado=estado;
    }

    public String getTelCelular(){
        return telCelular;
    }

    public void setTelCelular(String telCelular){
        this.telCelular=telCelular;
    }

    public String getTelEmergencia(){
        return telEmergencia;
    }

    public void setTelEmergencia(String telEmergencia){
        this.telEmergencia=telEmergencia;
    }

    public String getFotografia(){
        return fotografia;
    }

    public void setFotografia(String fotografia){
        this.fotografia=fotografia;
    }

    public int getEstatus(){
        return estatus;
    }

    public void setEstatus(int estatus){
        this.estatus=estatus;
    }

    public int getEnRuta(){
        return enRuta;
    }

    public void setEnRuta(int enRuta){
        this.enRuta=enRuta;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario=usuario;
    }
}
