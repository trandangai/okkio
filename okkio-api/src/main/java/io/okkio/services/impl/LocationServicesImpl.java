package io.okkio.services.impl;

import io.okkio.domain.Location;
import io.okkio.dto.LocationDto;
import io.okkio.dto.request.RequestLocationDto;
import io.okkio.dto.response.ResponseLocationDto;
import io.okkio.mapper.LocationMapper;
import io.okkio.mybatis.LocationMybatis;
import io.okkio.repository.LocationRepository;
import io.okkio.services.LocationServices;
import io.okkio.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * LocationServicesImpl
 */
@Slf4j
@Service
public class LocationServicesImpl extends BaseServiceImpl<Location, Long> implements LocationServices {

    public LocationServicesImpl(JpaRepository<Location, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationMybatis locationMybatis;

    @Override
    public List<LocationDto> getAllLocation() {
        List<Location> locations = super.findAll();
        List<LocationDto> result = new ArrayList<>();

        if (locations != null && !locations.isEmpty()) {
            for (Location location : locations) {
                LocationDto locationDto = locationMapper.toDto(location);
                locationDto.setImages(Stream.of(location.getImages().split(",")).collect(Collectors.toList()));
                result.add(locationDto);
            }
        }
        return result;
    }

    @Override
    public Location addLocation(RequestLocationDto dto) {
        Location util = locationMapper.toEntity(dto);
        util.setCreatedBy("System");
        util.setImages(String.join(",", dto.getImages()));
        return super.save(util);
    }

    @Override
    public LocationDto getLocationById(Long id) {
        Location location = locationRepository.findLocationById(id);
        if (location == null) {
            log.warn("LocationServicesImpl - getLocationById null with id: " + id);
            return null;
        }
        LocationDto result = locationMapper.toDto(location);
        result.setImages(Stream.of(location.getImages().split(",")).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void deleteLocationById(Long id) {
        locationRepository.deleteLocationById(id);
        log.warn("LocationServicesImpl - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean update(RequestLocationDto dto) {
        String images = null;
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            images = String.join(",", dto.getImages());
        }
        int isUpdated = locationMybatis.updateLocationByIds(dto.getId(), dto.getStatus(), dto.getName(), dto.getAddress(),
                dto.getPhone(), dto.getTitle(), dto.getDescription(), dto.getOpenTime(), dto.getOpenDay(),
                dto.getConceptStore(), images, dto.getParkingLot(), dto.getStore());
        if (isUpdated < 0) {
            log.warn("Can't update Location with dto: " + dto);
            return false;
        }
        return true;
    }
}
