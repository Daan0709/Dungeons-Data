package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.Item;
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
                        try {
                            character.removeItem(key);
                            PersistenceManager.saveAccountsToAzure();
                            return Response.ok().build();
                        } catch (Exception e){
                            e.printStackTrace();
                            return Response.status(Response.Status.BAD_REQUEST).build();
                        }
                    }
                }
                return Response.status(Response.Status.NOT_FOUND).build();
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
            try {
                if (character != null) {
                    for (Item key : character.getItemlist().keySet()) {
                        if (key.getNaam().equals(itemname)) {
                            if (method.equals("increase")) {
                                character.increaseItemAmount(key, 1);
                                PersistenceManager.saveAccountsToAzure();
                                return Response.ok().build();
                            } else if (method.equals("decrease")) {
                                if (character.getItemlist().get(key) == 1) {
                                    PersistenceManager.saveAccountsToAzure();
                                    character.removeItem(key);
                                    return Response.ok().build();
                                }
                                character.decreaseItemAmount(key, 1);
                                PersistenceManager.saveAccountsToAzure();
                                return Response.ok().build();
                            }
                        }
                    }
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            } catch (Exception e){
                e.printStackTrace();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("new")
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(@Context SecurityContext sc, @PathParam("charactername") String charactername, String jsonBody){
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
                try {
                    JsonObject object = Json.createReader(new StringReader(jsonBody)).readObject();
                    String name = object.getString("name");
                    String description = object.getString("description");
                    double weight = Double.parseDouble(object.getString("weight"));
                    int amount = Integer.parseInt(object.getString("amount"));
                    if (name.equals("") || description.equals("")){
                        throw new Exception("Can't add item without name or description");
                    }
                    Item item = new Item(name, description, weight);
                    character.addItem(item);
                    character.setItemAmount(item, amount);
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok(item).build();
                } catch (Exception e){
                    e.printStackTrace();
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
