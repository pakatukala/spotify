package com.org.pk.spotify.custom.logging;

import com.google.gson.JsonObject;
import org.slf4j.Marker;

public interface ComposeMarkers<M extends ComposeMarkers<M>> {
    M bookmark(String bookmark);
    M data(String data);
    M info(JsonObject info);

    M info(Object info);

    Marker collate();
}

