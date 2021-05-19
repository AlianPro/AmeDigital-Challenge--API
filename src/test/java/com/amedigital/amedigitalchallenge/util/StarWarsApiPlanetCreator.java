package com.amedigital.amedigitalchallenge.util;

import com.amedigital.amedigitalchallenge.domain.StarWarsApiPlanet;

import java.util.List;

public class StarWarsApiPlanetCreator {

    public static StarWarsApiPlanet createValidStarWarsApiPlanet(){
        StarWarsApiPlanet starWarsApiPlanet = new StarWarsApiPlanet();
        starWarsApiPlanet.setName("Earth");
        starWarsApiPlanet.setTerrain("Brazil");
        starWarsApiPlanet.setClimate("Tropical");
        starWarsApiPlanet.setFilms(List.of("Star Wars IV","Star Wars V"));
        return starWarsApiPlanet;
    }
}