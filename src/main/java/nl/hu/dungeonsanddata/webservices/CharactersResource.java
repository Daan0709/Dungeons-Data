package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/characters")
public class CharactersResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacters(@Context SecurityContext sc) {
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }

        if (currentAccount != null){
            return Response.ok(currentAccount.getCharacters()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
