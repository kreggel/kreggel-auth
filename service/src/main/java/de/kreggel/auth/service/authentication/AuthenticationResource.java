package de.kreggel.auth.service.authentication;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by oliverklette on 1/1/16.
 */
@Path("/authentication")
public class AuthenticationResource {
    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(Credentials credentials) {

        try {

            // Authenticate the user using the credentials provided
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // TODO validate the password with database and throw an Exception if the credentials are invalid
    }

    private String issueToken(String username) {
        // TODO Issue a JWT token and return the issued token
        String token="";
        return token;
    }
}
