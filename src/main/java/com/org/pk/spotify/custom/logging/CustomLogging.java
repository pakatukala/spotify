package com.org.pk.spotify.custom.logging;


import com.org.pk.spotify.context.ContextUtility;
import com.org.pk.spotify.context.RequestContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomLogging {

    @Autowired
    @Setter
    ContextUtility contextUtility;

    public void setUpRequestContext(RequestContext requestContext, String trigger) {

        String rootSpanId = UUID.randomUUID().toString();

        Map<String, Object> metaContext = new HashMap<>();

        metaContext.put("traceId", requestContext.getTraceId());
        metaContext.put("spanId", requestContext.getSpanId());

        Map<String, String> logContext = initLogContext(requestContext, rootSpanId, trigger);

        contextUtility.initExecutionContext(logContext, metaContext);

    }

    private Map<String, String> initLogContext(RequestContext requestContext, String rootSpanId, String trigger) {
        Map<String, String> logContext = new HashMap<>();
        logContext.put("traceId", requestContext.getTraceId());
        logContext.put("spanId", requestContext.getSpanId());
        logContext.put("rootSpanId", rootSpanId);
        logContext.put("requestId", UUID.randomUUID().toString());
        logContext.put("token", requestContext.getToken());
        logContext.put("artistName", requestContext.getArtistName());
        logContext.put("trigger", trigger);
        return logContext;
    }
}
