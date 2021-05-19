package com.amedigital.amedigitalchallenge.repository;

import com.amedigital.amedigitalchallenge.domain.Planet;
import com.amedigital.amedigitalchallenge.util.PlanetCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Planet Repository")
class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    @DisplayName("Save persists planet when Successful")
    void save_PersistPlanet_WhenSuccessful(){
        Planet planetToBeSaved = PlanetCreator.createPlanetToBeSaved();
        Planet planetSaved = this.planetRepository.save(planetToBeSaved);
        Assertions.assertThat(planetSaved).isNotNull();
        Assertions.assertThat(planetSaved.getId()).isNotNull();
        Assertions.assertThat(planetSaved.getName()).isEqualTo(planetToBeSaved.getName());
    }

    @Test
    @DisplayName("Find by name returns list of planet when Successful")
    void findByName_ReturnsListOfPlanet_WhenSuccessful(){
        Planet planetToBeSaved = PlanetCreator.createPlanetToBeSaved();
        Planet planetSaved = this.planetRepository.save(planetToBeSaved);
        String name = planetSaved.getName();
        List<Planet> planets = this.planetRepository.findByName(name);
        Assertions.assertThat(planets).isNotEmpty().contains(planetSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list when planet is not found")
    void findByName_ReturnsEmptyList_WhenPlanetIsNotFound (){
        List<Planet> planets = this.planetRepository.findByName("Mars");
        Assertions.assertThat(planets).isEmpty();
    }

    @Test
    @DisplayName("Delete removes planet when Successful")
    void delete_RemovesPlanet_WhenSuccessful(){
        Planet planetToBeSaved = PlanetCreator.createPlanetToBeSaved();
        Planet planetSaved = this.planetRepository.save(planetToBeSaved);
        this.planetRepository.delete(planetSaved);
        Optional<Planet> planetOptional = this.planetRepository.findById(planetSaved.getId());
        Assertions.assertThat(planetOptional).isEmpty();
    }
}