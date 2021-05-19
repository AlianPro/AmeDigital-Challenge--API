package com.amedigital.amedigitalchallenge.service;

import com.amedigital.amedigitalchallenge.domain.*;
import com.amedigital.amedigitalchallenge.exception.BadRequestException;
import com.amedigital.amedigitalchallenge.mapper.PlanetMapper;
import com.amedigital.amedigitalchallenge.repository.PlanetRepository;
import com.amedigital.amedigitalchallenge.requests.PlanetPostRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PlanetService {
    private final PlanetRepository planetRepository;

    final String URI_SEARCH = "https://swapi.dev/api/planets/?search=";
    final String URI_PAGE_PLANET = "https://swapi.dev/api/planets/?page=";

    public Planet save(PlanetPostRequestBody planetPostRequestBody){
        Planet planet = PlanetMapper.INSTANCE.toPlanet(planetPostRequestBody);

        List<StarWarsApiPlanet> resultsPlanet = new RestTemplate().exchange(this.URI_SEARCH + planet.getName(),
                HttpMethod.GET,
                null,
                StarWarsApi.class).getBody().getResults();

        long count = resultsPlanet.stream()
                .map(StarWarsApiPlanet::getFilms)
                .flatMap(p->p.stream().map(String::length))
                .count();
        planet.setCountFilms(count);
        return planetRepository.save(planet);
    }

    public List<Planet> listAllPlanets(){
        return planetRepository.findAll();
    }

    public List<StarWarsApiPlanet> listAllPlanetsOfSwapi(){
        int id = 1;
        List<StarWarsApiPlanet> totalPlanet = new ArrayList<>();

        while(true) {
            try {
                totalPlanet.addAll(new RestTemplate().exchange(this.URI_PAGE_PLANET+id, HttpMethod.GET,
                        null,
                        StarWarsApi.class).getBody().getResults());
            }catch (Exception e) {
                break;
            }
            id++;
        }
        return totalPlanet;
    }

    public List<Planet> listPlanetsFilteredByName(String name){
        return planetRepository.findByName(name);
    }

    public Planet getSpecificPlanetByIdOrThrowBadRequestException(long id){
        return planetRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Planet ID not found"));
    }

    public void delete(long id){
        planetRepository.delete(getSpecificPlanetByIdOrThrowBadRequestException(id));
    }
}
