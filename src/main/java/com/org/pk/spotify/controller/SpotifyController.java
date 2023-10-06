package com.org.pk.spotify.controller;


import com.org.pk.spotify.context.RequestContext;
import com.org.pk.spotify.custom.exceptions.CustomException;
import com.org.pk.spotify.handler.SpotifyHandler;
import com.org.pk.spotify.helpers.controllerhelpers.ControllerHelper;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyHandler spotifyHandler;

    @Autowired
    ControllerHelper controllerHelper;

    @GetMapping("/getTop10Albums")
    public ResponseEntity<?> getTop10Albums() throws IOException, ParseException, SpotifyWebApiException {
        return ResponseEntity.status(HttpStatus.OK).body(spotifyHandler.getTop10Albums());
    }

    @GetMapping("/getTop10")
    public ResponseEntity<?> getByArtist(@RequestHeader HttpHeaders headers)  {
        try {
            RequestContext requestContext = controllerHelper.extractHeaders(headers);
            return ResponseEntity.status(HttpStatus.OK).body(spotifyHandler.getTopTracksByArtist(requestContext));
        }catch (Throwable ex){
            return controllerHelper.respond(CustomException.wrap(ex));
        }
    }
}
