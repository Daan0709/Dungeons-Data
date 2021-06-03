package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("/addcharacter/{accountId}")
public class addCharacterResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCharacterJsonData(@PathParam("accountId") String accountId, String jsonBody) {
        Account currentAccount = null;
        for (Account a : Account.getAllAccounts()){
            if (a.getAccountId() == Integer.parseInt(accountId)){
                currentAccount = a;
            }
        }
        Character character;
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
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(character).build();
    }
}
