package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.Item;
import nl.hu.dungeonsanddata.domain.Skill;
import nl.hu.dungeonsanddata.persistence.PersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

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
}
