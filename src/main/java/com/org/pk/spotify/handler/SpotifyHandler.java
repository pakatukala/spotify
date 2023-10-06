package com.org.pk.spotify.handler;

import com.neovisionaries.i18n.CountryCode;
import com.org.pk.spotify.response.codes.AbstractDomCode;
import com.org.pk.spotify.custom.exceptions.CustomException;
import com.org.pk.spotify.context.RequestContext;
import com.org.pk.spotify.custom.logging.Markers;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SpotifyHandler {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyHandler.class);

    public SpotifyHandler() {}

    public List<AlbumSimplified> getTop10Albums() throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken("")
                .build();
        try {
            SearchAlbumsRequest request = spotifyApi.searchAlbums("tag:new").build();
            AlbumSimplified[] albums = request.execute().getItems();
            logger.info(new Markers().bookmark("ALBUMS").info(albums).collate(), StringUtils.EMPTY);
            return Arrays.asList(albums);
        } catch (ParseException e) {
            // Handle the ParseException
            // Log the exception or return an appropriate response
            throw new RuntimeException("Error parsing HTTP response", e);
        }
    }

    public List<String> getTopTracksByArtist(RequestContext requestContext) {
        List<String> topTracksList = new ArrayList<>();

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(requestContext.getToken())
                .build();

        try {
            // Perform an artist search
            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(requestContext.getArtistName()).build();
            Artist[] artists = searchArtistsRequest.execute().getItems();

            if (artists.length > 0) {
                // Get the artist ID of the first matching artist
                String artistId = artists[0].getId();

                // Get the artist's top tracks in a specific market (e.g., US)
                GetArtistsTopTracksRequest getTopTracksRequest = spotifyApi
                        .getArtistsTopTracks(artistId, CountryCode.valueOf("US"))
                        .build();

                Track[] topTracks = getTopTracksRequest.execute();

                // Populate the list with top track names
                for (Track track : topTracks) {
                    topTracksList.add(track.getName());
                }
            } else {
                CustomException customException = new CustomException(AbstractDomCode.ARTIST_NOT_FOUND);
                throw customException;
            }
        } catch (Exception exception) {
            CustomException customException = CustomException.wrap(exception, AbstractDomCode.ARTIST_NOT_FOUND);
            logger.info(new Markers().bookmark("ALBUMS").info(customException).collate(), StringUtils.EMPTY);
            throw customException;
        }
        logger.info(new Markers().bookmark("ALBUMS").info(topTracksList).collate(), StringUtils.EMPTY);
        return topTracksList;
    }

}
