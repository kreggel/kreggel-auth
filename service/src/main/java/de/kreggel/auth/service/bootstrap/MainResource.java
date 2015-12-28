package de.kreggel.auth.service.bootstrap;

import de.kreggel.auth.service.status.StatusResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * The versioned main entry point for this REST interface.
 * This entry point sits behind the api/ URL.
 */
public class MainResource extends AbstractResource {

    public static final String STATUS_RESOURCE = "/status";
    public static final String SETUP_RESOURCE = "/setup";

    public static final Set<String> KNOWN_RESOURCES;

    static {
        KNOWN_RESOURCES = new HashSet<>();
        Method[] methods = MainResource.class.getMethods();
        for (Method method : methods) {
            Path pathAnnotation = method.getAnnotation(Path.class);
            if (pathAnnotation != null) {
                KNOWN_RESOURCES.add(pathAnnotation.value());
            }
        }
    }

    /**
     * Shows a welcome message as String with all possible endpoints.
     *
     * @return a welcome message as String with all possible endpoints.
     */
    @GET
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        return "Welcome to the provisioning service. Please use one of these endpoints: "
                + KNOWN_RESOURCES.toString();
    }

    /**
     * Returns the status resource.
     *
     * @return the status resource.
     */
    @Path(STATUS_RESOURCE)
    public StatusResource getStatus() {
        return rc.getResource(StatusResource.class);
    }

}
