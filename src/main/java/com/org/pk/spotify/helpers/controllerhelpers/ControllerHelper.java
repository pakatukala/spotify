package com.org.pk.spotify.helpers.controllerhelpers;

import com.org.pk.spotify.constants.ControllerConstants;
import com.org.pk.spotify.context.RequestContext;
import com.org.pk.spotify.custom.exceptions.CustomError;
import com.org.pk.spotify.custom.exceptions.CustomException;
import com.org.pk.spotify.utils.CommonObjectUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ControllerHelper {

    @Autowired
    @Setter
    CommonObjectUtils commonObjectUtils;

    public RequestContext extractHeaders(HttpHeaders headers) {
        String traceId = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.TRACE_ID));
        String spanId = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.SPAN_ID));
        String token = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.TOKEN));
        String artistName = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.ARTIST_NAME));
        return new RequestContext().setTraceId(traceId).setSpanId(spanId).setToken(token).setArtistName(artistName);
    }

    public ResponseEntity<?> respond(CustomException error){
        CustomError customError = new CustomError(error);
        ResponseEntity<CustomError> responseEntity = new ResponseEntity<>(customError, error.getStatusCode());
        return responseEntity;
    }
}
