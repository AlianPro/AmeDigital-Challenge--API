package com.amedigital.amedigitalchallenge.controller;

import com.amedigital.amedigitalchallenge.domain.Planet;
import com.amedigital.amedigitalchallenge.domain.StarWarsApiPlanet;
import com.amedigital.amedigitalchallenge.exception.BadRequestException;
import com.amedigital.amedigitalchallenge.requests.PlanetPostRequestBody;
import com.amedigital.amedigitalchallenge.service.PlanetService;
import com.amedigital.amedigitalchallenge.util.PlanetCreator;
import com.amedigital.amedigitalchallenge.util.PlanetPostRequestBodyCreator;
import com.amedigital.amedigitalchallenge.util.StarWarsApiPlanetCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PlanetControllerTest {

    @InjectMocks
    private PlanetController planetController;
    @Mock
    private PlanetService planetServiceMock;

    @BeforeEach
    void setUP(){
        BDDMockito.when(planetServiceMock.save(ArgumentMatchers.any(PlanetPostRequestBody.class)))
                .thenReturn(PlanetCreator.createValidPlanet());

        BDDMockito.when(planetServiceMock.listAllPlanets()).thenReturn(List.of(PlanetCreator.createValidPlanet()));

        BDDMockito.when(planetServiceMock.listAllPlanetsOfSwapi())
                .thenReturn(List.of(StarWarsApiPlanetCreator.createValidStarWarsApiPlanet()));

        BDDMockito.when(planetServiceMock.listPlanetsFilteredByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PlanetCreator.createValidPlanet()));

        BDDMockito.when(planetServiceMock.getSpecificPlanetByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(PlanetCreator.createValidPlanet());

        BDDMockito.doNothing().when(planetServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("save returns planet when successful")
    void save_ReturnsPlanet_WhenSuccessful(){
        Planet planet = planetController.save(PlanetPostRequestBodyCreator.createPlanetPostRequestBody()).getBody();
        Assertions.assertThat(planet).isNotNull().isEqualTo(PlanetCreator.createValidPlanet());
    }

    @Test
    @DisplayName("list all returns list of planet when successful")
    void listall_ReturnsListOfPlanets_WhenSuccessful(){
        String expectedName = PlanetCreator.createValidPlanet().getName();
        List<Planet> planets = planetController.listAllPlanets().getBody();
        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(planets.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("list all returns list of planetSwapi when successful")
    void listall_ReturnsListOfPlanetsSwapi_WhenSuccessful(){
        String expectedName = PlanetCreator.createValidPlanet().getName();
        List<StarWarsApiPlanet> planets = planetController.listAllPlanetsOfSwapi().getBody();
        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(planets.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of planet when successful")
    void findByName_ReturnsListOfPlanets_WhenSuccessful(){
        String expectedName = PlanetCreator.createValidPlanet().getName();
        List<Planet> planets = planetController.listPlanetsFilteredByName("Earth").getBody();
        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(planets.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of planet when planet is not found")
    void findByName_ReturnsEmptyListOfPlanet_WhenPlanetIsNotFound(){
        BDDMockito.when(planetServiceMock.listPlanetsFilteredByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Planet> planets = planetController.listPlanetsFilteredByName("Mars").getBody();
        Assertions.assertThat(planets)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getSpecificPlanetByIdOrThrowBadRequestException returns planet when successful")
    void getSpecificPlanetByIdOrThrowBadRequestException_ReturnsPlanets_WhenSuccessful(){
        Long expectedId = PlanetCreator.createValidPlanet().getId();
        Planet planet = planetController.getSpecificPlanetByIdOrThrowBadRequestException(1L).getBody();
        Assertions.assertThat(planet).isNotNull();
        Assertions.assertThat(planet.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("getSpecificPlanetByIdOrThrowBadRequestException throws BadRequestException when planet is not found")
    void getSpecificPlanetByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenPlanetIsNotFound(){
        BDDMockito.when(planetServiceMock.getSpecificPlanetByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenThrow(new BadRequestException("Planet ID not found"));

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(()->planetController.getSpecificPlanetByIdOrThrowBadRequestException(2L));
    }

    @Test
    @DisplayName("delete removes planet when successful")
    void delete_RemovesPlanet_WhenSuccessful(){
        Assertions.assertThatCode(()->planetController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = planetController.delete(1L);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}