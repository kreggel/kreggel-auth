package de.kreggel.auth.service.registration;

import de.kreggel.auth.core.api.UserService;
import de.kreggel.auth.core.persistence.jpa.entity.User;
import de.kreggel.auth.service.authentication.Credentials;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by oliverklette on 1/2/16.
 */

    @Path("/registration")
    public class RegistrationResource {
        @POST
        @Produces("application/json")
        @Consumes("application/x-www-form-urlencoded")
        public Response authenticateUser(Userdata userdata) {
            try {
                UserService userService=new UserService();
                //TODO validate the email address by sending an activation link to it
                userService.createUser(userdata.getUsername(),userdata.getPassword(),userdata.getEmail());
                //TODO Return the metadata of the created user
                return Response.ok().build();

            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }
}
