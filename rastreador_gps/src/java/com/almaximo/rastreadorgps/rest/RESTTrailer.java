
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.core.ControllerTrailer;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Trailer;
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
@Path("trailer")
public class RESTTrailer{
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response save(@FormParam("datosTrailer") @DefaultValue("") String datosTrailer,
                         @FormParam("token") @DefaultValue("") String token) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Trailer t=new Trailer();
        ControllerLogin cl=new ControllerLogin();
        ControllerTrailer ct=new ControllerTrailer();
        
        try{
            if(cl.validarToken(token)){
                t=gson.fromJson(datosTrailer, Trailer.class);
                if(t.getNumeroTrailer()==0){
                    if(ct.duplicado(t)){
                        out="""
                            {"error":"Ya existe un registro con esta placa"}
                            """;
                    }else{
                        ct.insertarTrailer(t);
                        out=gson.toJson(t);
                    }
                } else{                    
                    ct.actualizarTrailer(t);
                    out=gson.toJson(t);               
                }
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
        Trailer t=new Trailer();
        List<Trailer> lt=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerTrailer ct=new ControllerTrailer();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(e!=null && cl.validarToken(e.getUsuario().getLastToken())){
                lt=ct.getAll();
                out=gson.toJson(lt);
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

    @Path("activarTrailer")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response activar(@FormParam("datosTrailer") @DefaultValue("") String datosTrailer,
                            @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Trailer t=new Trailer();
        ControllerLogin cl=new ControllerLogin();
        ControllerTrailer ct=new ControllerTrailer();
        
        try{
            if(cl.validarToken(token)){
                t=gson.fromJson(datosTrailer, Trailer.class);
                ct.activarTrailer(t);
                out=gson.toJson(t);
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
    
    @Path("eliminarTrailer")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response eliminarTrailer(@FormParam("datosTrailer") @DefaultValue("") String datosTrailer,
                                    @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Trailer t=new Trailer();
        ControllerLogin cl=new ControllerLogin();
        ControllerTrailer ct=new ControllerTrailer();
        
        try{
            if(cl.validarToken(token)){
                t=gson.fromJson(datosTrailer, Trailer.class);
                ct.eliminarTrailer(t);
                out=gson.toJson(t);
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
