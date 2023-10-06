package com.org.pk.spotify.custom.exceptions;

import com.google.gson.JsonObject;
import com.org.pk.spotify.response.codes.AbstractDomCode;
import com.org.pk.spotify.response.codes.DomCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {
    static final long serialVersionUID = 1L;

    DomCode errorCode;

    JsonObject info;

    HttpStatus statusCode;

   public CustomException(DomCode errorCode, HttpStatus statusCode) {
       super(errorCode.getMessage());
       this.errorCode = errorCode;
       this.statusCode = statusCode;
   }

   public CustomException(DomCode errorCode){
       this(errorCode, errorCode.getAppCode().getStatusCode());
   }

   private static String message(DomCode errorCode) {
       return String.format("%s: %s", errorCode.getAppCode(), errorCode.getMessage());
   }

   public static CustomException wrap(Throwable ex){
       return wrap(ex, AbstractDomCode.UNKNOWN_ERROR);
   }

   public static CustomException wrap(Throwable ex, DomCode errorCode){
       CustomException error = null;
       if(ex instanceof CustomException){
           error = (CustomException) ex;
       } else {
           log.error("UNKNOWN_ERROR", ex);
           error = new CustomException(errorCode);
       }
       return error;
   }

}
