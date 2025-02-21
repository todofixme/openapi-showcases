package de.codecentric.javaspring.web;

import org.springframework.http.HttpStatus;

public record Problem(String type, String title, int status, String detail) {
    public Problem(String title, int status, String detail) {
        this("about:blank", title, status, detail);
    }

    public Problem(HttpStatus status, Exception ex) {
        this(getType(status), status.getReasonPhrase(), status.value(), ex.getMessage());
    }

    public Problem(int status, Exception ex) {
        this(HttpStatus.valueOf(status).getReasonPhrase(), status, ex.getMessage());
    }

    public Problem(HttpStatus status, String detail) {
        this(status.getReasonPhrase(), status.value(), detail);
    }

    private static String getType(HttpStatus status) {
        return String.format("https://www.rfc-editor.org/rfc/rfc9110.html#name-%d-%s", status.value(),
                status.name().toLowerCase().replace("_", "-"));
    }
}
