package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.Stat;
import nl.hu.dungeonsanddata.persistence.PersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.StringReader;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}/hp")
    public Response adjustHitpoints(@Context SecurityContext sc, String jsonBody, @PathParam("name") String name){
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
                    int currentHp = Integer.parseInt(object.getString("currenthp"));
                    int maxHp = Integer.parseInt(object.getString("maxhp"));
                    if (currentHp > maxHp){
                        currentHp = maxHp;
                    }
                    currentCharacter.setHitpoints(currentHp);
                    currentCharacter.setMaxHitpoints(maxHp);
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

    @PUT
    @RolesAllowed("user")
    @Path("{name}/stats")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setStats(@Context SecurityContext sc, String jsonBody, @PathParam("name") String name){
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
                    int strength = Integer.parseInt(object.getString("strength"));
                    int dexterity = Integer.parseInt(object.getString("dexterity"));
                    int constitution = Integer.parseInt(object.getString("constitution"));
                    int intelligence = Integer.parseInt(object.getString("intelligence"));
                    int wisdom = Integer.parseInt(object.getString("wisdom"));
                    int charisma = Integer.parseInt(object.getString("charisma"));

                    currentCharacter.setStat("Strength", strength);
                    currentCharacter.setStat("Dexterity", dexterity);
                    currentCharacter.setStat("Constitution", constitution);
                    currentCharacter.setStat("Intelligence", intelligence);
                    currentCharacter.setStat("Wisdom", wisdom);
                    currentCharacter.setStat("Charisma", charisma);
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok().build();
                }
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("{name}/currency")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setCurrency(@Context SecurityContext sc, String jsonBody, @PathParam("name") String name){
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
                    int platinum = Integer.parseInt(object.getString("platinum"));
                    int gold = Integer.parseInt(object.getString("gold"));
                    int silver = Integer.parseInt(object.getString("silver"));
                    int copper = Integer.parseInt(object.getString("copper"));

                    currentCharacter.setCurrency("Platinum", platinum);
                    currentCharacter.setCurrency("Gold", gold);
                    currentCharacter.setCurrency("Silver", silver);
                    currentCharacter.setCurrency("Copper", copper);
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok().build();
                }
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("{name}/xp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setXp(@Context SecurityContext sc, String jsonBody, @PathParam("name") String name){
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
                    int setxp = Integer.parseInt(object.getString("setxp"));
                    int addxp = Integer.parseInt(object.getString("addxp"));
                    if (setxp < 0){                                     // If set xp is given a negative value:
                        throw new Exception("Xp can't be set below 0"); // Throw exception
                    }
                    if (currentCharacter.getExperience() != setxp){     // If set xp is given a different value, prioritize that over adding xp.
                        currentCharacter.setExperience(setxp);
                        PersistenceManager.saveAccountsToAzure();
                        return Response.ok().build();
                    }
                    currentCharacter.addExperience(addxp);
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok().build();
                }
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
