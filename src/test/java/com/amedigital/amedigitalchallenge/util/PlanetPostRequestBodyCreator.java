package com.amedigital.amedigitalchallenge.util;

import com.amedigital.amedigitalchallenge.requests.PlanetPostRequestBody;

public class PlanetPostRequestBodyCreator {

    public static PlanetPostRequestBody createPlanetPostRequestBody(){
        return PlanetPostRequestBody.builder()
                .name(PlanetCreator.createPlanetToBeSaved().getName())
                .climate(PlanetCreator.createPlanetToBeSaved().getClimate())
                .terrain(PlanetCreator.createPlanetToBeSaved().getTerrain())
                .build();
    }
}
