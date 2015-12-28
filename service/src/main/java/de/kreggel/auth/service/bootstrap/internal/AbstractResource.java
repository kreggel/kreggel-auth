package de.kreggel.auth.service.bootstrap.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/** The abstract base resource Jax-Rs class combines methods to be used by all resource classes. */

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public abstract class AbstractResource {

    /** The UriInfo object of the underlying request. Is injected by the Jersey framework. */
    @Context
    protected UriInfo uriInfo;

    /** The HttpServletRequest object of the underlying request. Is injected by the Jersey
     * framework. */
    @Context
    protected HttpServletRequest request;

    @Context
    protected ResourceContext rc;

    protected Logger logger;

    protected Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(getClass());
        }

        return logger;
    }
}
