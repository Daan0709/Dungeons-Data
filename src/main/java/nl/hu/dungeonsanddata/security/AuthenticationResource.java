package nl.hu.dungeonsanddata.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.dungeonsanddata.domain.Account;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.security.Key;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;


@Path("login")
public class AuthenticationResource {
    public static final Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(String jsonBody){
        try {
            JsonObject object = Json.createReader(new StringReader(jsonBody)).readObject();
            String email = object.getString("email");
            String password = object.getString("password");

            if (!Account.isValid(email, password)){
                throw new Exception("Invalid Login!");
            }

            Account account = Account.getAccountByEmail(email);
            String token = createToken(account.getName(), account.getRole(), account.getAccountId());
            return Response.ok(new SimpleEntry<>("JWT", token)).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private String createToken(String email, String role, int accountId) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .claim("accountId", accountId)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
