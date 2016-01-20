package de.kreggel.auth.core.exception;

public class ErrorCodeBasedException extends Exception {
    private int code;
    private String phrase;

    public ErrorCodeBasedException(int code, String phrase) {
        super(code + " - " + phrase);
        this.code=code;
        this.phrase=phrase;
    }

    public ErrorCodeBasedException(int code, String phrase, Throwable throwable) {
        super(code + " - " + phrase, throwable);
        this.code=code;
        this.phrase=phrase;
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }
}
