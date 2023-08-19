
package com.almaximo.rastreadorgps.rest;

import com.almaximo.rastreadorgps.core.ControllerClave;
import com.almaximo.rastreadorgps.core.ControllerLogin;
import com.almaximo.rastreadorgps.model.Clave;
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author rocha
 */
@Path("clave")
public class RESTClave{
    @Path("getClave")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response getClave(@FormParam("token") @DefaultValue("") String token,
                             @FormParam("nombreClave") @DefaultValue("") String nombreClave) throws Exception{
        String out=null;
        Gson gson=new Gson();
        Clave c=new Clave();
        ControllerLogin cl=new ControllerLogin();
        ControllerClave cc=new ControllerClave();
        
        try{
            if(cl.validarToken(token)){
                c=cc.getClave(nombreClave);
                out=gson.toJson(c);
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
