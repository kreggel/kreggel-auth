package de.kreggel.auth.service.bootstrap.internal;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.net.MalformedURLException;
import java.net.URL;

import static de.kreggel.auth.service.bootstrap.internal.ErrorResponse.JSON_NAME;

@JsonRootName(JSON_NAME)
public class ErrorResponse {

    protected static final String JSON_NAME = "error";

    private final String requestId;
    private final String message;
    private final int code;
    private final URL details;

    public ErrorResponse(String message, int code, String requestId) {
        this.message = message;
        this.code = code;
        this.requestId = requestId;
        try {
            this.details = new URL("https://unknown/errorcode/" + code);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public ErrorResponse() {
        message = "unknown error occured";
        code = -1;
        requestId = null;
        details = null;
    }

    /**
     * Gets the technical, english only, error message.
     *
     * @return the message describing the error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the error code.
     *
     * @return the error code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the unique id of the request. Can be used within support requests.
     *
     * @return the id of the current request.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Gets the URL to a page describing the error in detail with possible causes and solutions.
     *
     * @return the URL to the error details.
     */
    public URL getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "requestId='" + requestId + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", details=" + details +
                '}';
    }
}