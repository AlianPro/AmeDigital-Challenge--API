package com.amedigital.amedigitalchallenge.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanetPostRequestBody {
    @NotBlank
    private String name;
    @NotBlank
    private String climate;
    @NotBlank
    private String terrain;
}
