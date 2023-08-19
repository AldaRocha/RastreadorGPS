
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author rocha
 */
@Path("log")
public class RESTLogin extends Application{
    @Path("in")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response login(@FormParam("datos") @DefaultValue("") String datos) throws Exception{        
        String out=null;
        Gson gson=new Gson();
        Usuario u=new Usuario();
        Empleado e=new Empleado();
        ControllerLogin cl=new ControllerLogin();
        
        try{
            u=gson.fromJson(datos, Usuario.class);            
            e=cl.login(u.getNombreUsuario(), u.getContrasenia());
            if(e!=null){
                e.getUsuario().setLastToken();                
                cl.generarToken(e.getUsuario().getIdUsuario(), e.getUsuario().getLastToken());
                out=gson.toJson(e);
            } else{
                out="""
                    {"error":"Las credenciales son incorrectas, intente de nuevo."}
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
    
    @Path("out")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response logout(@FormParam("empleado") @DefaultValue("") String empleado) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        ControllerLogin cl=new ControllerLogin();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(cl.eliminarToken(e)){
                out="""
                    {"ok":"Eliminacion de Token Correcta."}
                    """;
            } else{
                out="""
                    {"error":"Eliminacion de token no realizada."}
                    """;
            }
        } catch(JsonParseException jpe){
            out="""
                {"error":"Eliminacion no concedido"}
                """;
            jpe.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
