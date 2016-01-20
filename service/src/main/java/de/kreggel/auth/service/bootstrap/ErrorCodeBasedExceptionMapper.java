package de.kreggel.auth.service.bootstrap;

import de.kreggel.auth.core.exception.ErrorCodeBasedException;
import de.kreggel.auth.service.bootstrap.internal.ErrorResponse;
import de.kreggel.auth.service.bootstrap.internal.WebInvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ErrorCodeBasedExceptionMapper implements ExceptionMapper<ErrorCodeBasedException> {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorCodeBasedExceptionMapper.class);

    @Override
    public Response toResponse(ErrorCodeBasedException exception) {
        LOG.error(exception.getPhrase(), exception);

        Status status = Status.fromStatusCode(exception.getCode());
        int httpCode = status.getStatusCode();
        String phrase = status.getReasonPhrase();

        // TODO: introduce error code resolver
        ErrorResponse errorResponse = new ErrorResponse(exception.getPhrase(), exception.getCode(), phrase, httpCode, WebInvocationContext.getInstance().getRequestId());

        return Response.status(httpCode).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
    }
}