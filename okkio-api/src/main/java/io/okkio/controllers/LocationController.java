package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.Categories;
import io.okkio.domain.Location;
import io.okkio.domain.Util;
import io.okkio.dto.LocationDto;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.request.RequestLocationDto;
import io.okkio.dto.request.RequestUtilDto;
import io.okkio.dto.response.ResponseLocationDto;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.LocationServices;
import io.okkio.services.UtilServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class    LocationController {

    private LocationServices locationServices;

    public LocationController(LocationServices locationServices) {
        this.locationServices = locationServices;
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<Location> add(@RequestBody RequestLocationDto dto, HttpServletRequest httpServletRequest) {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (dto.getImages() == null || dto.getImages().isEmpty()) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        dto.setEmail(email);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, locationServices.addLocation(dto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, locationServices.getAllLocation());
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestLocationDto dto, HttpServletRequest httpServletRequest) {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        if (dto.getId() == null) {
            return ResponseUtil.ok(Constants.MESSAGE_BAD_REQUEST + " with Location Id null ", null);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        dto.setEmail(email);
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, locationServices.update(dto));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> delete(@Param("id") Long id) {
        LocationDto util = locationServices.getLocationById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        locationServices.deleteLocationById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<LocationDto> getLocationById(@Param("id") Long id) {
        LocationDto result = locationServices.getLocationById(id);
        if (result == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    private boolean validatedStatus(String status) {
        return Constants.ACTIVATED_STATUS.equals(status)
                || Constants.DEACTIVATED_STATUS.equals(status);
    }
}
