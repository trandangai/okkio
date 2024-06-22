package io.okkio.services;


import io.okkio.domain.Location;
import io.okkio.dto.LocationDto;
import io.okkio.dto.request.RequestLocationDto;
import io.okkio.dto.response.ResponseLocationDto;

import java.util.List;

public interface LocationServices {
    List<LocationDto> getAllLocation();
    Location addLocation(RequestLocationDto dto);
    LocationDto getLocationById(Long id);
    void deleteLocationById(Long id);
    boolean update(RequestLocationDto dto);
}
