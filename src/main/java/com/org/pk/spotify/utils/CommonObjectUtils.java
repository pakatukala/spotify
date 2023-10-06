package com.org.pk.spotify.utils;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CommonObjectUtils {
    private CommonObjectUtils() {
    }

    public static CommonObjectUtils getInstance() {
        return Creator.INSTANCE;
    }

    @PostConstruct
    public void init() {
        Creator.INSTANCE = this;
    }

    public <T extends Object> T getFirst(Iterable<T> list) {
        return get(list, 0);
    }

    public <T extends Object> T getFirst(T[] list) {
        return ArrayUtils.get(list, 0);
    }

    public <T extends Object> T get(Iterable<T> list, int index) {
        return index >= IterableUtils.size(list) ? null : IterableUtils.get(list, index);
    }

    public static class Creator {
        private static CommonObjectUtils INSTANCE = new CommonObjectUtils();
    }
}
