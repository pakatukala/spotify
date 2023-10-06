package com.org.pk.spotify.response.codes;

public interface AbstractDomCode {
    DomCode UNKNOWN_ERROR = new DomCode("UNKNOWN_ERROR", "Internal error occurred", AbstractAppCode.UNKNOWN_ERROR);

    DomCode ARTIST_NOT_FOUND = new DomCode("ARTIST_NOT_FOUND", "No artist found", AbstractAppCode.INVALID_REQUEST);

    DomCode TRACE_ID_INVALID = new DomCode("INVALID_TRACE_ID", "Invalid or empty traceId", AbstractAppCode.TRACE_ID_INVALID);

    DomCode SPAN_ID_INVALID = new DomCode("INVALID_SPAN_ID", "Invalid or empty spanId", AbstractAppCode.SPAN_ID_INVALID);

    DomCode REQUEST_UNSUPPORTED = new DomCode("INVALID_REQUEST_ID", "Request not supported on this entity", AbstractAppCode.REQUEST_ID_INVALID);
}
