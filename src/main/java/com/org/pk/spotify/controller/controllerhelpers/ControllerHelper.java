package com.org.pk.spotify.controller.controllerhelpers;

import com.org.pk.spotify.constants.ControllerConstants;
import com.org.pk.spotify.context.ContextUtility;
import com.org.pk.spotify.context.RequestContext;
import com.org.pk.spotify.custom.exceptions.CustomError;
import com.org.pk.spotify.custom.exceptions.CustomException;
import com.org.pk.spotify.utils.CommonObjectUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ControllerHelper {

    @Autowired
    @Setter
    CommonObjectUtils commonObjectUtils;

    @Autowired
    ContextUtility contextUtility;

    public RequestContext extractHeaders(HttpHeaders headers) {
        String traceId = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.TRACE_ID));
        String spanId = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.SPAN_ID));
        String token = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.TOKEN));
        String artistName = commonObjectUtils.getFirst(headers.get(ControllerConstants.RestHeaders.ARTIST_NAME));
        return new RequestContext().setTraceId(traceId).setSpanId(spanId).setToken(token).setArtistName(artistName);
    }

    public <T> ResponseEntity<T> respond(T entity, HttpStatus status){
        ResponseEntity<T> responseEntity = new ResponseEntity<>(entity, respondHeaders(), status);
        return responseEntity;
    }

    public ResponseEntity<?> respond(CustomException error){
        CustomError customError = new CustomError(error);
        ResponseEntity<CustomError> responseEntity = new ResponseEntity<>(customError, error.getStatusCode());
        return responseEntity;
    }

    public HttpHeaders respondHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-trace-id", contextUtility.getTraceId());
        headers.add("x-span-id", contextUtility.getSpanId());
        headers.add("token", contextUtility.getToken());
        headers.add("artist-name", contextUtility.getArtistName());
        contextUtility.clearExecutionContext();
        return headers;
    }
}
