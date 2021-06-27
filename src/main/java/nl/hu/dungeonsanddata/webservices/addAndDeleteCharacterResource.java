package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
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

@Path("/addcharacter")
public class addAndDeleteCharacterResource {

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCharacterJsonData(@Context SecurityContext sc, String jsonBody) {
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }
        Character character;
        if (currentAccount != null) {
            try {
                JsonObject object = Json.createReader(new StringReader(jsonBody)).readObject();
                character = new Character(object.getString("name"),
                        object.getString("race"),
                        Integer.parseInt(object.getString("experience")),
                        Integer.parseInt(object.getString("hitpoints")),
                        object.getString("class"), 0);
                character.setStat("Strength", Integer.parseInt(object.getString("strength")));
                character.setStat("Charisma", Integer.parseInt(object.getString("charisma")));
                character.setStat("Wisdom", Integer.parseInt(object.getString("wisdom")));
                character.setStat("Dexterity", Integer.parseInt(object.getString("dexterity")));
                character.setStat("Constitution", Integer.parseInt(object.getString("constitution")));
                character.setStat("Intelligence", Integer.parseInt(object.getString("intelligence")));
                currentAccount.addCharacter(character);
                PersistenceManager.saveAccountsToAzure();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(character).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path("/{charactername}/delete")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCharacter(@Context SecurityContext sc, @PathParam("charactername") String characterName) {
        Account currentAccount = null;
        if (sc.getUserPrincipal() instanceof Account){
            currentAccount = (Account) sc.getUserPrincipal();
        }
        if (currentAccount != null) {
            try {
                for (Character character : currentAccount.getCharacters()){
                    if (character.getNaam().equals(characterName)){
                        currentAccount.removeCharacter(character);
                        PersistenceManager.saveAccountsToAzure();
                        return Response.ok().build();
                    }
                }
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
