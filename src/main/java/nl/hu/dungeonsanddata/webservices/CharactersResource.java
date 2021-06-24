package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
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

    @GET
    @RolesAllowed("user")
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecificCharacter(@Context SecurityContext sc, @PathParam("name") String name){
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }

        if (currentAccount != null){
            Character character = null;
            for (Character c : currentAccount.getCharacters()){
                if (c.getNaam().equals(name)){
                    character = c;
                }
            }

            if (character != null){
                return Response.ok(character).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @RolesAllowed("user")
    @Path("/{name}/{spellslotfunction}")
    public Response updateSpellslot(@Context SecurityContext sc, @PathParam("name") String name, @PathParam("spellslotfunction") String function){
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }
        try {
            if (currentAccount != null) {
                Character character = null;
                for (Character c : currentAccount.getCharacters()) {
                    if (c.getNaam().equals(name)) {
                        character = c;
                    }
                }

                if (character != null) {
                    if (function.equals("clearspellslots")) {
                        character.resetSpellslots();
                        return Response.ok().build();
                    } else if (function.equals("usespellslot")) {
                        character.useSpellslot();
                        return Response.ok().build();
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                } else {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
