package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.Spell;
import nl.hu.dungeonsanddata.domain.Spellslot;
import nl.hu.dungeonsanddata.persistence.PersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.StringReader;

@Path("spellslot")
public class spellslotResource {
    @PUT
    @RolesAllowed("user")
    @Path("/{name}/clearspellslots")
    public Response updateSpellslot(@Context SecurityContext sc, @PathParam("name") String name){
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
                    character.resetSpellslots();
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok().build();
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

    @PUT
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}/{spellslotlevel}/use")
    public Response useSpellslot(@Context SecurityContext sc, @PathParam("name") String name, @PathParam("spellslotlevel") int spellslotlevel){
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }
        if (currentAccount != null) {
            try {
                Character currentCharacter = null;
                for (Character character : currentAccount.getCharacters()){
                    if (character.getNaam().equals(name)){
                        currentCharacter = character;
                    }
                }

                Spellslot spellslotToUse = currentCharacter.getSpecificSpellslot(spellslotlevel);
                spellslotToUse.useSpellslot();
                PersistenceManager.saveAccountsToAzure();
                return Response.ok().build();
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}/{spellslotlevel}/remove")
    public Response removeSpellslot(@Context SecurityContext sc, @PathParam("name") String name, @PathParam("spellslotlevel") int spellslotlevel){
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }
        if (currentAccount != null) {
            try {
                Character currentCharacter = null;
                for (Character character : currentAccount.getCharacters()){
                    if (character.getNaam().equals(name)){
                        currentCharacter = character;
                    }
                }

                Spellslot spellslotToUse = currentCharacter.getSpecificSpellslot(spellslotlevel);
                spellslotToUse.removeSpellslot();
                PersistenceManager.saveAccountsToAzure();
                return Response.ok().build();
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}/spellslot")
    public Response adjustSpellslots(@Context SecurityContext sc, String jsonBody, @PathParam("name") String name){
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }
        if (currentAccount != null) {
            try {
                Character currentCharacter = null;
                for (Character character : currentAccount.getCharacters()){
                    if (character.getNaam().equals(name)){
                        currentCharacter = character;
                    }
                }

                if (currentCharacter != null){
                    JsonObject object = Json.createReader(new StringReader(jsonBody)).readObject();
                    int amount = Integer.parseInt(object.getString("amount"));
                    int spellslotlevel = Integer.parseInt(object.getString("spellslotlevel"));
                    if (amount < 0){
                        throw new Exception("Amount of spellslots can't go below 0");
                    }
                    Spellslot spellslot = new Spellslot(spellslotlevel, amount);
                    currentCharacter.addSpellslot(spellslot);
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok().build();
                }

                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
