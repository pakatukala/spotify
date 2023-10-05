package com.org.pk.spotify.custom.logging;

import com.balajeetm.mystique.util.gson.lever.JsonLever;
import com.google.gson.JsonObject;
import org.slf4j.Marker;

import java.util.HashMap;
import java.util.Map;

public class Markers implements ComposeMarkers<Markers> {

    private Map<String, Object> markers;

    private JsonLever jsonLever;

    public Markers() {
        markers = new HashMap<>();
        jsonLever = jsonLever.getInstance();

    }
    @Override
    public Markers bookmark(String bookmark) {
        return mark("bookmark", bookmark);
    }

    @Override
    public Markers data(String data) {
        return mark("data", data);
    }

    @Override
    public Markers info(JsonObject info) {
        return jsonLever.isNotNull(info) ? mark("info", info) : null;
    }

    @Override
    public Markers info(Object info) {
        return mark("info", info);
    }

    @Override
    public Marker collate() {
        return net.logstash.logback.marker.Markers.appendEntries(markers);
    }

    private Markers mark(String key, Object value) {
        markers.put(key, value);
        return this;
    }
}

