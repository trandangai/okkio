package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.Location;
import io.okkio.dto.ContactDto;
import io.okkio.dto.LocationDto;
import io.okkio.dto.request.RequestLocationDto;
import io.okkio.dto.response.ResponseLocationDto;
import io.okkio.services.LocationServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private LocationServices locationServices;

    public ContactController(LocationServices locationServices) {
        this.locationServices = locationServices;
    }

    @Value("${contact.title}")
    private String title;

    @Value("${contact.address}")
    private String address;

    @Value("${contact.phone}")
    private String phone;

    @Value("${contact.email}")
    private String email;

    @Value("${contact.facebook}")
    private String facebook;

    @Value("${contact.instagram}")
    private String instagram;

    @GetMapping
    public ResponseEntity<ContactDto> getContact() {
        List<ResponseLocationDto> locations = locationServices.getAllLocation();
        ContactDto result = new ContactDto();
        result.setTitle(title);
        result.setAddress(address);
        result.setEmail(email);
        result.setPhone(phone);
        result.setFacebook(facebook);
        result.setInstagram(instagram);
        result.setLocations(locations);
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<LocationDto> getContactById(@Param("id") Long id) {
        LocationDto result = locationServices.getLocationById(id);
        if (result == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

}
