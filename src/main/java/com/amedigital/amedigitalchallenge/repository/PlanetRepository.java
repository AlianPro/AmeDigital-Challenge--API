package com.amedigital.amedigitalchallenge.repository;

import com.amedigital.amedigitalchallenge.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanetRepository extends JpaRepository<Planet,Long> {
    @Query("SELECT planet FROM Planet planet WHERE planet.name LIKE %:name%")
    List<Planet> findByName(String name);
}
