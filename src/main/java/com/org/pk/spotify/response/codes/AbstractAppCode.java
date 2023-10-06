package com.org.pk.spotify.response.codes;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public interface AbstractAppCode {

    AppCode TRACE_ID_INVALID = new AppCode("INVALID_TRACE_ID", "Invalid or empty traceId", HttpStatus.BAD_REQUEST);

    AppCode SPAN_ID_INVALID = new AppCode("INVALID_SPAN_ID", "Invalid or empty spanId", HttpStatus.BAD_REQUEST);

    AppCode REQUEST_ID_INVALID = new AppCode("INVALID_REQUEST_ID", "Invalid or empty requestId", HttpStatus.BAD_REQUEST);

    AppCode TOKEN_INVALID = new AppCode("INVALID_TOKEN", "Invalid or empty token", HttpStatus.BAD_REQUEST);

    AppCode SUCCESS = new AppCode("SUCCESS", "Success", HttpStatus.OK);

    AppCode ERROR = new AppCode("ERROR", "Error", HttpStatus.INTERNAL_SERVER_ERROR);

    AppCode PLACEHOLDER = new AppCode("PLACEHOLDER", StringUtils.EMPTY, HttpStatus.I_AM_A_TEAPOT);

    AppCode UNKNOWN_ERROR = new AppCode("UNKNOWN_ERROR", "Internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    AppCode INVALID_REQUEST = new AppCode("INVALID_REQUEST", "Invalid request.", HttpStatus.INTERNAL_SERVER_ERROR);


}
