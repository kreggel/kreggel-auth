package de.kreggel.auth.service.bootstrap;

import de.kreggel.auth.service.bootstrap.internal.CatchAllExceptionMapper;
import de.kreggel.auth.service.bootstrap.internal.JacksonObjectMapperProvider;
import de.kreggel.auth.service.bootstrap.internal.WebInvocationContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@ApplicationPath(ServiceResourceConfig.SERVICE_PATH)
public class ServiceResourceConfig extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceResourceConfig.class);

    public static final String SERVICE_PATH = "/api/1";

    @Context
    private ResourceContext resourceContext;

    public ServiceResourceConfig() {
        super(MainResource.class, CatchAllExceptionMapper.class, ErrorCodeBasedExceptionMapper.class);
        packages("de.kreggel");

        Map<String, Object> map = new HashMap<>();
        // avoid IllegalStateExceptions when Spring Security Filter tries to handle AccessDeniedExceptions.
        // Jersey per default commits the response and so kills all subsequent filters
        map.put(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, Boolean.TRUE);
        addProperties(map);
    }

    @PostConstruct
    public void initialize() {
        // bind the resource context to the invocation context to make it accessible to everyone
        WebInvocationContext.getInstance().setResourceContext(resourceContext);
        LOGGER.info("Service Application has been initialized properly.");
        LOGGER.info("Application is available under ApplicationPath: " + SERVICE_PATH);
        System.out.println("Service Application has been initialized properly.");
    }
}