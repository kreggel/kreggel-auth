package de.kreggel.auth.service.authentication;

import de.kreggel.auth.core.api.UserService;
import de.kreggel.auth.core.persistence.jpa.entity.User;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by oliverklette on 1/1/16.
 */
@Path("/authentication")
public class AuthenticationResource {
    @GET
    @Produces("application/json")
    public Response authenticateUser(ContainerRequest containerRequest) {
        try {

            // Authenticate the user using the credentials provided
            // String username = credentials.getUsername();
            // String password = credentials.getPassword();
            //Get the authentification passed in HTTP headers parameters
            String auth = containerRequest.getHeaderString("authorization");

            //If the user does not have the right (does not provide any HTTP Basic Auth)
            if(auth == null){
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            //lap : loginAndPassword
            String[] credentials = BasicAuth.decode(auth);
            String username=credentials[0];
            String password=credentials[1];
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        UserService userService=new UserService();
        List<User> userList=userService.findUsers(username);
        if (userList.size()!=1) {
            throw new Exception("Couldn't find the username");
        }
        else {
            User user=userList.get(0);
            String userPassword=user.getPassword();
            if(userPassword.equals(password)) {
                System.out.println("user authentication successful");
            }
            else {
                throw new Exception("invlaid password!");
            }

        }
    }

    private String issueToken(String username) {
        // TODO Issue a JWT token and return the issued token
        String token="";
        return token;
    }
}
