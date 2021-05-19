package com.amedigital.amedigitalchallenge.mapper;

import com.amedigital.amedigitalchallenge.domain.Planet;
import com.amedigital.amedigitalchallenge.requests.PlanetPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PlanetMapper {
    public static final PlanetMapper INSTANCE = Mappers.getMapper(PlanetMapper.class);
    public abstract Planet toPlanet(PlanetPostRequestBody planetPostRequestBody);
}
