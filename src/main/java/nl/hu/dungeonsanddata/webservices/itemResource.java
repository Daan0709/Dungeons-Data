package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.Item;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("{charactername}/item")
public class itemResource {

    @DELETE
    @RolesAllowed("user")
    @Path("/remove/{item}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeItem(@Context SecurityContext sc, @PathParam("charactername") String charactername, @PathParam("item") String itemname){
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }

        if (currentAccount != null){
            Character character = null;
            for (Character c : currentAccount.getCharacters()){
                if (c.getNaam().equals(charactername)){
                    character = c;
                    }
                }

            if (character != null){
                for (Item key : character.getItemlist().keySet()) {
                    if (key.getNaam().equals(itemname)) {
                        character.removeItem(key);
                        return Response.ok().build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("/{method}/{item}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response adjustItemAmount(@Context SecurityContext sc, @PathParam("charactername") String charactername, @PathParam("item") String itemname, @PathParam("method") String method) {
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account) {
            currentAccount = (Account) sc.getUserPrincipal();
        }

        if (currentAccount != null) {
            Character character = null;
            for (Character c : currentAccount.getCharacters()) {
                if (c.getNaam().equals(charactername)) {
                    character = c;
                }
            }
            if (character != null){
                for (Item key : character.getItemlist().keySet()) {
                    if (key.getNaam().equals(itemname)) {
                        if (method.equals("increase")) {
                            character.increaseItemAmount(key, 1);
                            return Response.ok().build();
                        } else if (method.equals("decrease")){
                            if (character.getItemlist().get(key) == 1){
                                character.removeItem(key);
                                return Response.ok().build();
                            }
                            character.decreaseItemAmount(key, 1);
                            return Response.ok().build();
                        }
                    }
                }
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
