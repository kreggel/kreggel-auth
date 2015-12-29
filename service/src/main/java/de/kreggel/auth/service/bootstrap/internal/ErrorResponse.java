package de.kreggel.auth.service.bootstrap.internal;

import com.fasterxml.jackson.annotation.JsonRootName;

import static de.kreggel.auth.service.bootstrap.internal.ErrorResponse.JSON_NAME;

@JsonRootName(JSON_NAME)
public class ErrorResponse {

    protected static final String JSON_NAME = "error";

    private final String requestId;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    public ErrorResponse(String message, int code, String httpPhrase, int httpCode, String requestId) {
        this.message = message;
        this.code = code;
        this.requestId = requestId;
        this.httpStatus = new HttpStatus(httpCode, httpPhrase);
    }

    public ErrorResponse() {
        message = "unknown error occured";
        code = -1;
        requestId = null;
        httpStatus = HttpStatus.UNKNOWN;
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "requestId='" + requestId + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", httpStatus='" + httpStatus + '\'' +
                '}';
    }

    public static class HttpStatus {
        public static final HttpStatus UNKNOWN = new HttpStatus();

        private final int code;
        private final String phrase;

        public HttpStatus(){
            code = 500;
            phrase = "Internal Server Error";
        }

        public HttpStatus(int code, String phrase) {
            this.code = code;
            this.phrase = phrase;
        }

        public int getCode() {
            return code;
        }

        public String getPhrase() {
            return phrase;
        }

        @Override
        public String toString() {
            return "HttpStatus{" +
                    "code=" + code +
                    ", phrase='" + phrase + '\'' +
                    '}';
        }
    }
}