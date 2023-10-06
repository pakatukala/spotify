package com.org.pk.spotify.custom.logging;

import com.balajeetm.mystique.util.gson.lever.JsonLever;
import com.google.gson.JsonObject;
import com.org.pk.spotify.response.codes.AppCode;
import com.org.pk.spotify.custom.exceptions.CustomException;
import com.org.pk.spotify.response.codes.DomCode;
import org.slf4j.Marker;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class Markers implements ComposeMarkers<Markers> {

    private Map<String, Object> markers;

    private JsonLever jsonLever;

    public Markers() {
        markers = new HashMap<>();
        jsonLever = jsonLever.getInstance();

    }
    public Markers statusCode(HttpStatus statusCode) {
        return mark("statusCode", statusCode.value());
    }

    public Markers statusCode(Integer statusCode) {
        return mark("statusCode", statusCode);
    }

    public Markers error(CustomException error) {
        return error != null ? mark("errorMessage", error.getMessage()).info(error.getInfo()).error(error.getErrorCode()) : this;
    }

    public Markers error(DomCode errorCode) {
        return mark("domainErrorCode", errorCode.getCode()).error(errorCode.getAppCode());
    }

    public Markers error(AppCode appCode) {
        return mark("appErrorCode", appCode.getCode()).statusCode(appCode.getStatusCode());
    }

    public Markers bookmark(String bookmark) {
        return mark("bookmark", bookmark);
    }

    public Markers data(String data) {
        return mark("data", data);
    }

    public Markers info(JsonObject info) {
        return jsonLever.isNotNull(info) ? mark("info", info) : null;
    }

    public Markers info(Object info) {
        return mark("info", info);
    }

    public Marker collate() {
        return net.logstash.logback.marker.Markers.appendEntries(markers);
    }

    private Markers mark(String key, Object value) {
        markers.put(key, value);
        return this;
    }
}

