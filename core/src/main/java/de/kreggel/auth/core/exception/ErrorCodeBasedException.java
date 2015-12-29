package de.kreggel.auth.core.exception;

public class ErrorCodeBasedException extends Exception {
    private int code;
    private String phrase;

    public ErrorCodeBasedException(int code, String phrase) {
        super(code + " - " + phrase);
    }

    public ErrorCodeBasedException(int code, String phrase, Throwable throwable) {
        super(code + " - " + phrase, throwable);
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }
}
