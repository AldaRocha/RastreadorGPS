
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerEmpleado;
import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.model.Empleado;
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
@Path("empleado")
public class RESTEmpleado{
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response save(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado,
                         @FormParam("token") @DefaultValue("") String token) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        ControllerLogin cl=new ControllerLogin();
        ControllerEmpleado ce=new ControllerEmpleado();

        try{
            if(cl.validarToken(token)){                
                e=gson.fromJson(datosEmpleado, Empleado.class);
                if(Long.parseLong(e.getNumeroEmpleado())==0){
                    if(ce.duplicadoEmpleado(e)){
                        out="""
                            {"error":"Ya existe un empleado con esta informacion"}
                            """;
                    } else if(ce.duplicadoUsuario(e)){
                        out="""
                            {"error":"Ya existe este nombre de usuario"}
                            """;
                    } else{
                        ce.insertarEmpleado(e);
                        out=gson.toJson(e);
                    }
                } else{
                    ce.actualizarEmpleado(e);
                    out=gson.toJson(e);
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
        List<Empleado> le=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerEmpleado ce=new ControllerEmpleado();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(e!=null && cl.validarToken(e.getUsuario().getLastToken())){
                le=ce.getAllEmpleado(e.getUsuario().getNivel().getNombreNivel());
                out=gson.toJson(le);
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
    public Response activar(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado,
                            @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        ControllerLogin cl=new ControllerLogin();
        ControllerEmpleado ce=new ControllerEmpleado();
        
        try{
            if(cl.validarToken(token)){
                e=gson.fromJson(datosEmpleado, Empleado.class);
                ce.activarEmpleado(e);
                out=gson.toJson(e);
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
    public Response eliminar(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado,
                             @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        ControllerLogin cl=new ControllerLogin();
        ControllerEmpleado ce=new ControllerEmpleado();
        
        try{
            if(cl.validarToken(token)){
                e=gson.fromJson(datosEmpleado, Empleado.class);
                ce.eliminarEmpleado(e);
                out=gson.toJson(e);
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
    
    @Path("cambiarContrasenia")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response cambiarContrasenia(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado,
                                       @FormParam("token") @DefaultValue("") String token){
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        ControllerLogin cl=new ControllerLogin();
        ControllerEmpleado ce=new ControllerEmpleado();
        
        try{
            if(cl.validarToken(token)){
                e=gson.fromJson(datosEmpleado, Empleado.class);
                ce.cambiarContraseña(e);
                out=gson.toJson(e);
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
