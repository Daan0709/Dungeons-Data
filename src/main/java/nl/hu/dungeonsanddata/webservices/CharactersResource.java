package nl.hu.dungeonsanddata.webservices;

import nl.hu.dungeonsanddata.domain.Account;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/characters/{accountId}")
public class CharactersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacters(@PathParam("accountId") String accountId) {
        Account currentAccount = null;
        for (Account a : Account.getAllAccounts()){
            if (a.getAccountId() == Integer.parseInt(accountId)){
                currentAccount = a;
            }
        }

        if (currentAccount != null){
            return Response.ok(currentAccount.getCharacters()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
