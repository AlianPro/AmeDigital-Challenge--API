package com.amedigital.amedigitalchallenge.util;

import com.amedigital.amedigitalchallenge.domain.Planet;

public class PlanetCreator {

    public static Planet createPlanetToBeSaved(){
        return Planet.builder()
                .name("Earth")
                .climate("Tropical")
                .terrain("Brazil")
                .build();
    }

    public static Planet createValidPlanet(){
        return Planet.builder()
                .id(1L)
                .name("Earth")
                .terrain("Brazil")
                .climate("Tropical")
                .countFilms(5L)
                .build();
    }
}
