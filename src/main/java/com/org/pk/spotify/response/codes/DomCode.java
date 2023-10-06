package com.org.pk.spotify.response.codes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
public class DomCode {

    protected String code;

    @EqualsAndHashCode.Exclude protected String message;
    @EqualsAndHashCode.Exclude protected AppCode appCode;

    public DomCode(String code, String message, AppCode appCode) {
        this.code = code;
        this.message = message;
        this.appCode = appCode;
    }

    public DomCode(String code, String message) {
        this(code, message, AbstractAppCode.PLACEHOLDER);
    }
}
