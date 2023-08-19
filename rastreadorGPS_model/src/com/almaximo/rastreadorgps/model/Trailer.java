
package com.almaximo.rastreadorgps.model;

/**
 *
 * @author rocha
 */
public class Trailer{
    private int numeroTrailer;
    private String placa;
    private String marca;
    private String modelo;
    private String fotografia;
    private int estatus;
    private int enRuta;

    public Trailer(int numeroTrailer, String placa, String marca, String modelo, String fotografia, int estatus, int enRuta){
        this.numeroTrailer=numeroTrailer;
        this.placa=placa;
        this.marca=marca;
        this.modelo=modelo;
        this.fotografia=fotografia;
        this.estatus=estatus;
        this.enRuta=enRuta;
    }

    public Trailer(){
    }

    public int getNumeroTrailer(){
        return numeroTrailer;
    }

    public void setNumeroTrailer(int numeroTrailer){
        this.numeroTrailer=numeroTrailer;
    }

    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String placa){
        this.placa=placa;
    }

    public String getMarca(){
        return marca;
    }

    public void setMarca(String marca){
        this.marca=marca;
    }

    public String getModelo(){
        return modelo;
    }

    public void setModelo(String modelo){
        this.modelo=modelo;
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
}
