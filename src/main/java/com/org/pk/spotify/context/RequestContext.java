package com.org.pk.spotify.context;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class RequestContext {
    String traceId;
    String spanId;
    String requestId = UUID.randomUUID().toString();
    String token;
    String artistName;
}
