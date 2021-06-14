package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("/registeraccount")
public class registerAccountResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAccount(String jsonBody){

        Account account;
        try {
            JsonObject object = Json.createReader(new StringReader(jsonBody)).readObject();
            if (!object.getString("password").equals(object.getString("retype_password"))){
                throw new Exception("Passwords do not match");
            } else {
                account = new Account(object.getString("email"), object.getString("password"));
            }
        } catch (Exception e){
            if (e.getMessage().equals("Passwords do not match")){
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(account).build();
    }
}
