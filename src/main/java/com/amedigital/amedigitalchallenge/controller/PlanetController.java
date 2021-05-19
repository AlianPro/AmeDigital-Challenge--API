package com.amedigital.amedigitalchallenge.controller;

import com.amedigital.amedigitalchallenge.domain.Planet;
import com.amedigital.amedigitalchallenge.domain.StarWarsApiPlanet;
import com.amedigital.amedigitalchallenge.requests.PlanetPostRequestBody;
import com.amedigital.amedigitalchallenge.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ame")
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetService planetService;

    @Operation(summary = "List all planets")
    @GetMapping(path="/list/planets")
    public ResponseEntity<List<Planet>> listAllPlanets(){
        return ResponseEntity.ok(planetService.listAllPlanets());
    }

    @Operation(summary = "List planets and filter them by name")
    @GetMapping(path="/list/planets/search")
    public ResponseEntity<List<Planet>> listPlanetsFilteredByName(@RequestParam String name){
        return ResponseEntity.ok(planetService.listPlanetsFilteredByName(name));
    }


    @Operation(summary = "Get a specific planet")
    @GetMapping(path="/list/planets/{id}")
    public ResponseEntity<Planet> getSpecificPlanetByIdOrThrowBadRequestException(@PathVariable long id){
        return ResponseEntity.ok(planetService.getSpecificPlanetByIdOrThrowBadRequestException(id));
    }

    @Operation(summary = "List all planets Swapi")
    @GetMapping(path="/list/planets/swapi")
    public ResponseEntity<List<StarWarsApiPlanet>> listAllPlanetsOfSwapi(){
        return ResponseEntity.ok(planetService.listAllPlanetsOfSwapi());
    }

    @Operation(summary = "Create a planet")
    @PostMapping
    public ResponseEntity<Planet> save(@RequestBody @Valid PlanetPostRequestBody planetPostRequestBody){
        return new ResponseEntity<>(planetService.save(planetPostRequestBody), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Successful Operation"),
            @ApiResponse(responseCode = "400",description = "When Planet Does Not Exist in The Database")
    })
    @Operation(summary = "Delete a planet")
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        planetService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
