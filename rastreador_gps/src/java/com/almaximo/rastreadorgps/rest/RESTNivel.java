
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.core.ControllerNivel;
import com.almaximo.rastreadorgps.model.Empleado;
import com.almaximo.rastreadorgps.model.Nivel;
import com.google.gson.Gson;
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

@Path("nivel")
public class RESTNivel{
    @Path("gestion")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response gestion(@FormParam("empleado") @DefaultValue("") String empleado) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        List<Nivel> n=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerNivel cn=new ControllerNivel();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(e!=null && cl.validarToken(e.getUsuario().getLastToken())){
                n=cn.accesos(e.getUsuario().getNivel().getNombreNivel());
                out=gson.toJson(n);
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
    
    @Path("niveles")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response getNiveles(@FormParam("empleado") @DefaultValue("") String empleado) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Empleado e=new Empleado();
        List<Nivel> n=new ArrayList<>();
        ControllerLogin cl=new ControllerLogin();
        ControllerNivel cn=new ControllerNivel();
        
        try{
            e=gson.fromJson(empleado, Empleado.class);
            if(e!=null && cl.validarToken(e.getUsuario().getLastToken())){
                n=cn.tiposNivel(e.getUsuario().getNivel().getNombreNivel());                
                out=gson.toJson(n);
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
