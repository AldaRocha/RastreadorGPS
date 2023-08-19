
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.core.ControllerPermiso;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Permiso;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rocha
 */
@Path("permiso")
public class RESTPermiso{
    @Path("actualizarPantallas")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response actualizarPantallas(@FormParam("datosPantallas") @DefaultValue("") String datosPantallas,
                                        @FormParam("token") @DefaultValue("") String token) throws Exception{
        String out=null;
        Gson gson=new Gson();
        List<Permiso> lp=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerPermiso cp=new ControllerPermiso();
        
        try{
            if(cl.validarToken(token)){
                Type listType=new TypeToken<List<Permiso>>(){}.getType();
                lp=gson.fromJson(datosPantallas, listType);
                cp.actualizarPantallas(lp);
                out=gson.toJson(lp);
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
        List<Permiso> lp=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerPermiso cp=new ControllerPermiso();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(e!=null && cl.validarToken(e.getUsuario().getLastToken())){
                lp=cp.getAll();
                out=gson.toJson(lp);
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
}
