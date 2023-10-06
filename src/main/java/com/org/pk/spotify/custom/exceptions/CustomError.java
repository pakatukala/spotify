package com.org.pk.spotify.custom.exceptions;

import com.balajeetm.mystique.core.module.GsonSerialiser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonObject;
import com.org.pk.spotify.response.codes.AppCode;
import com.org.pk.spotify.response.codes.DomCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
public class CustomError {
    String code;
    String message;

    @JsonSerialize(using = GsonSerialiser.class)
    @JsonInclude(Include.NON_NULL)
    JsonObject info;

    public CustomError(CustomException error) {
        this(error.getErrorCode());
        this.info = error.getInfo();
    }

    public CustomError(DomCode errorCode) {
        this(errorCode.getAppCode());
    }

    public CustomError(AppCode appCode) {
        this(appCode.getCode(), appCode.getMessage());
    }

    public CustomError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
