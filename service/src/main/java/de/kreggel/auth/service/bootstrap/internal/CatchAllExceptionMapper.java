package de.kreggel.auth.service.bootstrap.internal;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the catch all exception mapper that is invoked, when no other mapper exists. Normally this signals an
 * internal server error.
 */
@Provider
public class CatchAllExceptionMapper implements ExtendedExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(CatchAllExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {

        String cause = "an unhandled exception occured";

        // if it's client related, add the cause of the error to the message
        if (exception instanceof ClientErrorException || exception instanceof ParamException) {
            cause += ": " + exception.getMessage();
            LOG.debug(cause, exception);
        } else {
            LOG.error(cause, exception);
        }

        // TODO: introduce error code resolver
        ErrorResponse errorResponse = new ErrorResponse(cause, 1, WebInvocationContext.getInstance().getRequestId());

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public boolean isMappable(Exception exception) {
        return exception instanceof ClientErrorException;
    }
}