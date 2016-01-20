package de.kreggel.auth.service.registration;

import de.kreggel.auth.core.api.UserService;
import de.kreggel.auth.core.exception.ErrorCodeBasedException;
import de.kreggel.auth.core.persistence.jpa.entity.User;
import de.kreggel.auth.service.authentication.Credentials;
import de.kreggel.auth.service.bootstrap.ErrorCodeBasedExceptionMapper;

import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by oliverklette on 1/2/16.
 */

    @Path("/user")
    public class UserResource {
        @POST
        @Produces("application/json")
        @Consumes("application/json")
        public Response authenticateUser(User user) throws ErrorCodeBasedException {
            final Response response;

                UserService userService = new UserService();
                String username = user.getUsername();
                String password = user.getPassword();
                String email = user.getEmail();

                if (username.length()==0 || password.length()==0 || email.length()==0) {
                    throw new ErrorCodeBasedException(400, "At least one of the required fields is empty");
                }

                //TODO validate the email address by sending an activation link to it
                User coreUser = userService.createUser(username,password,email);
                user.setId(coreUser.getId());
                //TODO Return the metadata of the created user


                response = Response.ok(user).build();

                return response;

        }
}
