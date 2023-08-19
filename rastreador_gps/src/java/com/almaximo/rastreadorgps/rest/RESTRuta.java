
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.core.ControllerRuta;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Ruta;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rocha
 */
@Path("ruta")
public class RESTRuta{
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response save(@FormParam("datosRuta") @DefaultValue("") String datosRuta,
                         @FormParam("token") @DefaultValue("") String token) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Ruta r=new Ruta();
        ControllerLogin cl=new ControllerLogin();
        ControllerRuta cr=new ControllerRuta();
        
        try{
            if(cl.validarToken(token)){
                r=gson.fromJson(datosRuta, Ruta.class);
                if(r.getNumeroRuta()==0){
                    cr.insertarRuta(r);
                } else{
                    cr.actualizarRuta(r);
                }
                out=gson.toJson(r);
            } else{
                out="""
                    {"error":"Es posible que no estes registrado o no tengas permisos"}
                    """;
            }
        } catch(JsonParseException jpe){
            jpe.printStackTrace();
            out="""
                {"exception":"Formato JSON de Datos Incorrectos."}
                """;
        } catch(Exception ex){
            ex.printStackTrace();
            out="""
                {"exception":"?"}
                """;
            out=String.format(out, ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response getAll(@FormParam("empleado") @DefaultValue("") String empleado) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        List<Ruta> lr=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerRuta cr=new ControllerRuta();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(e!=null && cl.validarToken(e.getUsuario().getLastToken())){
                lr=cr.getAll();
                out=gson.toJson(lr);
            } else{
                out="""
                    {"error":"Es posible que no estes registrado o no tengas permisos"}
                    """;
            }
        } catch(Exception ex){
            ex.printStackTrace();
            out="""
                {"exception":"?"}
                """;
            out=String.format(out, ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("activar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response activar(@FormParam("datosRuta") @DefaultValue("") String datosRuta,
                            @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Ruta r=new Ruta();
        ControllerLogin cl=new ControllerLogin();
        ControllerRuta cr=new ControllerRuta();
        
        try{
            if(cl.validarToken(token)){
                r=gson.fromJson(datosRuta, Ruta.class);
                cr.reanudarRuta(r);
                out=gson.toJson(r);
            } else{
                out="""
                    {"error":"Es posible que no estes registrado o no tengas permisos"}
                    """;
            }
        } catch(JsonParseException jpe){
            jpe.printStackTrace();
            out="""
                {"exception":"Formato JSON de Datos Incorrectos."}
                """;
        } catch(Exception ex){
            ex.printStackTrace();
            out="""
                {"exception":"?"}
                """;
            out=String.format(out, ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response eliminar(@FormParam("datosRuta") @DefaultValue("") String datosRuta,
                             @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Ruta r=new Ruta();
        ControllerLogin cl=new ControllerLogin();
        ControllerRuta cr=new ControllerRuta();
        
        try{
            if(cl.validarToken(token)){
                r=gson.fromJson(datosRuta, Ruta.class);
                cr.terminarRuta(r);
                out=gson.toJson(r);
            } else{
                out="""
                    {"error":"Es posible que no estes registrado o no tengas permisos"}
                    """;
            }
        } catch(JsonParseException jpe){
            jpe.printStackTrace();
            out="""
                {"exception":"Formato JSON de Datos Incorrectos."}
                """;
        } catch(Exception ex){
            ex.printStackTrace();
            out="""
                {"exception":"?"}
                """;
            out=String.format(out, ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
