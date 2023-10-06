package com.org.pk.spotify.response.codes;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class AppCode {

    private String code;
    private String message;
    private HttpStatus statusCode;

    public AppCode(String code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public AppCode(String code, String message) {
        this(code, message, HttpStatus.I_AM_A_TEAPOT);
    }
}
