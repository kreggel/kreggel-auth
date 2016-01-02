package de.kreggel.auth.service.authentication;

import de.kreggel.auth.core.api.UserService;
import de.kreggel.auth.core.persistence.jpa.entity.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

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
