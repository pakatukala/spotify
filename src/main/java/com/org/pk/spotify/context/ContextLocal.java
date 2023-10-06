package com.org.pk.spotify.context;


import java.util.HashMap;

public class ContextLocal extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final ThreadLocal<ContextLocal> localContext = new ThreadLocal<>();

    public static ContextLocal currentContext(){
        ContextLocal context = localContext.get();
        if (context == null){
            context = new ContextLocal();
            localContext.set(context);
        }
        return context;
    }

    public static void purge(){
        localContext.remove();
    }

    public void init(){
        localContext.set(this);
    }
}
