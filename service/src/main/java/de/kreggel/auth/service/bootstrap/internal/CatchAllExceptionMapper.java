package de.kreggel.auth.service.bootstrap.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This is the catch all exception mapper that is invoked, when no other mapper exists. Normally this signals an
 * internal server error.
 */
@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(CatchAllExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        LOG.error("an unhandled exception occured", exception);

        Status status = Status.INTERNAL_SERVER_ERROR;
        String cause = "an unhandled exception occured";
        int httpCode = status.getStatusCode();
        String phrase = status.getReasonPhrase();

        if (exception instanceof WebApplicationException) {
            final Response response = ((WebApplicationException) exception).getResponse();
            httpCode = response.getStatusInfo().getStatusCode();
            phrase = response.getStatusInfo().getReasonPhrase();
        }

        // TODO: introduce error code resolver
        ErrorResponse errorResponse = new ErrorResponse(cause, 1, phrase, httpCode, WebInvocationContext.getInstance().getRequestId());

        return Response.status(httpCode).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
    }
}