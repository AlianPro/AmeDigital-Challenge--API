package com.amedigital.amedigitalchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Planet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The Planet name cannot be blank")
    private String name;
    @NotBlank(message = "The Planet terrain cannot be blank")
    private String terrain;
    @NotBlank(message = "The Planet climate cannot be blank")
    private String climate;
    private Long countFilms;
}
