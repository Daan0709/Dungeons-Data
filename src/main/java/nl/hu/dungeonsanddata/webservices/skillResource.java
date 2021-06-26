package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.*;
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

@Path("{charactername}/skill")
public class skillResource {

    @DELETE
    @RolesAllowed("user")
    @Path("/remove/{skill}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSkill(@Context SecurityContext sc, @PathParam("charactername") String charactername, @PathParam("skill") String skillName){
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
                for (Skill skill : character.getSkills()) {
                    if (skill.getNaam().equals(skillName)) {
                        try {
                            character.removeSkill(skill);
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

    @POST
    @Path("new")
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSkill(@Context SecurityContext sc, @PathParam("charactername") String charactername, String jsonBody){
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
                    if (name.equals("") || description.equals("")){
                        throw new Exception("Can't add skill without name or description");
                    }
                    Skill skill = new Skill(name, description);
                    character.addSkill(skill);
                    PersistenceManager.saveAccountsToAzure();
                    return Response.ok(skill).build();
                } catch (Exception e){
                    e.printStackTrace();
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
