package com.amedigital.amedigitalchallenge.service;

import com.amedigital.amedigitalchallenge.domain.Planet;
import com.amedigital.amedigitalchallenge.domain.StarWarsApiPlanet;
import com.amedigital.amedigitalchallenge.exception.BadRequestException;
import com.amedigital.amedigitalchallenge.repository.PlanetRepository;
import com.amedigital.amedigitalchallenge.util.PlanetCreator;
import com.amedigital.amedigitalchallenge.util.PlanetPostRequestBodyCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@ExtendWith(SpringExtension.class)
class PlanetServiceTest {

    @InjectMocks
    private PlanetService planetService;
    @Mock
    private PlanetRepository planetRepositoryMock;

    @BeforeEach
    void setUP(){
        BDDMockito.when(planetRepositoryMock.save(ArgumentMatchers.any(Planet.class)))
                .thenReturn(PlanetCreator.createValidPlanet());

        BDDMockito.when(planetRepositoryMock.findAll())
                .thenReturn(List.of(PlanetCreator.createValidPlanet()));

        BDDMockito.when(planetRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PlanetCreator.createValidPlanet()));

        BDDMockito.when(planetRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(PlanetCreator.createValidPlanet()));

        BDDMockito.doNothing().when(planetRepositoryMock).delete(ArgumentMatchers.any(Planet.class));
    }

    @Test
    @DisplayName("save returns planet when successful")
    void save_ReturnsPlanet_WhenSuccessful(){
        Planet planet = planetService.save(PlanetPostRequestBodyCreator.createPlanetPostRequestBody());
        Assertions.assertThat(planet).isNotNull().isEqualTo(PlanetCreator.createValidPlanet());
    }

    @Test
    @DisplayName("listAll returns list of planet when successful")
    void listAll_ReturnsListOfPlanets_WhenSuccessful(){
        String expectedName = PlanetCreator.createValidPlanet().getName();
        List<Planet> planets = planetService.listAllPlanets();
        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(planets.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of planetSwapi when successful")
    void listAll_ReturnsListOfPlanetsSwapi_WhenSuccessful(){
        List<StarWarsApiPlanet> planets = planetService.listAllPlanetsOfSwapi();
        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("findByName returns list of planet when successful")
    void findByName_ReturnsListOfPlanets_WhenSuccessful(){
        String expectedName = PlanetCreator.createValidPlanet().getName();
        List<Planet> planets = planetService.listPlanetsFilteredByName("Earth");
        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(planets.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of planet when planet is not found")
    void findByName_ReturnsEmptyListOfPlanet_WhenPlanetIsNotFound(){
        BDDMockito.when(planetRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Planet> planets = planetService.listPlanetsFilteredByName("Mars");
        Assertions.assertThat(planets)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns planet when successful")
    void findByIdOrThrowBadRequestException_ReturnsPlanets_WhenSuccessful(){
        Long expectedId = PlanetCreator.createValidPlanet().getId();
        Planet planet = planetService.getSpecificPlanetByIdOrThrowBadRequestException(1L);
        Assertions.assertThat(planet).isNotNull();
        Assertions.assertThat(planet.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when planet is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenPlanetIsNotFound(){
        BDDMockito.when(planetRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(()->planetService.getSpecificPlanetByIdOrThrowBadRequestException(2L));
    }

    @Test
    @DisplayName("delete removes planet when successful")
    void delete_RemovesPlanet_WhenSuccessful(){
        Assertions.assertThatCode(()->planetService.delete(1L))
                .doesNotThrowAnyException();
    }
}