package com.amedigital.amedigitalchallenge.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StarWarsApiPlanet {
     private String name;
     private String terrain;
     private String climate;
     private List<String> films;
}
