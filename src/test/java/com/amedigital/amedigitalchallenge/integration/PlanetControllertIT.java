package com.amedigital.amedigitalchallenge.integration;

import com.amedigital.amedigitalchallenge.domain.Planet;
import com.amedigital.amedigitalchallenge.domain.StarWarsApiPlanet;
import com.amedigital.amedigitalchallenge.repository.PlanetRepository;
import com.amedigital.amedigitalchallenge.requests.PlanetPostRequestBody;
import com.amedigital.amedigitalchallenge.util.PlanetCreator;
import com.amedigital.amedigitalchallenge.util.PlanetPostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlanetControllertIT {
    @Autowired
    @Qualifier("testRestTemplate")
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PlanetRepository planetRepository;

    @TestConfiguration
    @Lazy
    static class Config{
        @Bean(name="testRestTemplate")
        public TestRestTemplate testRestTemplateCreator(@Value("${local.server.port}") int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port);
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list all returns list of planet when successful")
    void listall_ReturnsListOfPlanets_WhenSuccessful(){
        Planet savedPlanet = planetRepository.save(PlanetCreator.createPlanetToBeSaved());
        String expectedName = savedPlanet.getName();

        List<Planet> exchange = testRestTemplate.exchange("/ame/list/planets",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Planet>>() {
                }).getBody();

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange)
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(exchange.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of planet when successful")
    void findByName_ReturnsListOfPlanets_WhenSuccessful(){
        Planet savedPlanet = planetRepository.save(PlanetCreator.createPlanetToBeSaved());
        String expectedName = savedPlanet.getName();
        String url = String.format("/ame/list/planets?search=%s",expectedName);
        List<Planet> planets = testRestTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Planet>>() {
                }).getBody();

        Assertions.assertThat(planets)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(planets.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of planet when planet is not found")
    void findByName_ReturnsEmptyListOfPlanet_WhenPlanetIsNotFound(){
        List<Planet> planets = testRestTemplate.exchange("/ame/list/planets?search=Mars",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Planet>>() {
                }).getBody();

        Assertions.assertThat(planets)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findById returns planet when successful")
    void findById_ReturnsPlanets_WhenSuccessful(){
        Planet savedPlanet = planetRepository.save(PlanetCreator.createPlanetToBeSaved());
        Long expectedId = savedPlanet.getId();
        Planet planet = testRestTemplate.getForObject("/ame/list/planets/{id}", Planet.class, expectedId);
        Assertions.assertThat(planet).isNotNull();
        Assertions.assertThat(planet.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("list all returns list of planetSwapi when successful")
    void listall_ReturnsListOfPlanetsSwapi_WhenSuccessful(){
        List<StarWarsApiPlanet> exchange = testRestTemplate.exchange("/ame/list/planets/swapi",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StarWarsApiPlanet>>() {
                }).getBody();

        Assertions.assertThat(exchange)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("save returns planet when successful")
    void save_ReturnsPlanet_WhenSuccessful(){
        PlanetPostRequestBody planetPostRequestBody = PlanetPostRequestBodyCreator.createPlanetPostRequestBody();
        ResponseEntity<Planet> planetResponseEntity = testRestTemplate.postForEntity("/ame",
                planetPostRequestBody,
                Planet.class);
        Assertions.assertThat(planetResponseEntity).isNotNull();
        Assertions.assertThat(planetResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(planetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(planetResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("delete removes planet when successful")
    void delete_RemovesPlanet_WhenSuccessful(){
        Planet savedPlanet = planetRepository.save(PlanetCreator.createPlanetToBeSaved());
        ResponseEntity<Void> planetResponseEntity = testRestTemplate.exchange("/ame/remove/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                savedPlanet.getId());

        Assertions.assertThat(planetResponseEntity).isNotNull();
        Assertions.assertThat(planetResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
