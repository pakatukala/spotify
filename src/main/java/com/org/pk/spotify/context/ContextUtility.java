package com.org.pk.spotify.context;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Component
public class ContextUtility {
    private ContextUtility() {
    }

    public static ContextUtility getInstance() {
        return Creator.INSTANCE;
    }

    @PostConstruct
    void init() {
        Creator.INSTANCE = this;
    }

    public String getTraceId() {
        return getLogContext().get("traceId");
    }

    public String getSpanId() {
        return getLogContext().get("spanId");
    }

    public String getToken() {
        return getLogContext().get("token");
    }

    public String getArtistName() {
        return getLogContext().get("artistName");
    }


    public Runnable decorateRunnable(Runnable runnable) {
        ContextLocal current = ContextLocal.currentContext();
        return () -> {
            contextualize(current);
            try {
                runnable.run();
            } finally {
                clearExecutionContext();
            }
        };
    }

    public <V> Callable<V> decorateCallable(Callable<V> callable) {
        ContextLocal current = ContextLocal.currentContext();
        return () -> {
            contextualize(current);
            try {
                return callable.call();
            } finally {
                clearExecutionContext();
            }
        };
    }

    public void clearExecutionContext() {
        MDC.clear();
        ContextLocal.purge();
    }


    private void contextualize(ContextLocal contextLocal) {
        contextLocal.init();
        updateMdc(getLogContext(contextLocal));
    }

    private Map<String, String> getLogContext() {
        return getLogContext(ContextLocal.currentContext());
    }

    private Map<String, String> getLogContext(ContextLocal contextLocal) {
        Map<String, String> logContext = null;
        if (null != contextLocal) {
            logContext = (Map<String, String>) contextLocal.getOrDefault("LOG_CONTEXT", new HashMap<>());
        }
        return logContext;
    }

    private Map<String, Object> getMetaContext(ContextLocal contextLocal) {
        Map<String, Object> metaContext = null;
        if (null != contextLocal) {
            metaContext = (Map<String, Object>) contextLocal.getOrDefault("META_CONTEXT", new HashMap<>());
        }
        return metaContext;
    }

    private void updateMdc(Map<String, String> logContext) {
        MDC.setContextMap(logContext);
    }

    public ContextLocal initExecutionContext(Map<String, String> logContext, Map<String, Object> metaContext) {
        ContextLocal contextLocal = new ContextLocal();
        contextLocal.put("LOG_CONTEXT", logContext);
        contextLocal.put("META_CONTEXT", metaContext);

        contextLocal.init();
        updateMdc(logContext);
        return contextLocal;
    }

    public static class Creator {
        public static ContextUtility INSTANCE = new ContextUtility();
    }
}
