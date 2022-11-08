package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.common.EntityStatus;
import io.okkio.domain.Blogs;
import io.okkio.domain.Careers;
import io.okkio.domain.Categories;
import io.okkio.domain.CustomerSupports;
import io.okkio.domain.PartnerShip;
import io.okkio.dto.*;
import io.okkio.dto.request.RequestCategoryDto;
import io.okkio.dto.request.RequestLocationDto;
import io.okkio.dto.response.ResponseCategoryDto;
import io.okkio.dto.response.UserDtoResponse;
import io.okkio.mybatis.UserMybatis;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.CategoryServices;
import io.okkio.services.DiscoveryService;
import io.okkio.services.UserServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/discovery")
public class DiscoveryController {

    @Autowired
    DiscoveryService discoveryService;

    List<String> CUSTOMER_SUPPORT_TITLE = Arrays.asList("DELIVERY", "RETURN AND EXCHANGE", "SUPPORT");

    @GetMapping("/get-all-career")
    public ResponseEntity<List<CareerDto>> getAllCareers() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, discoveryService.getAllCareers());
    }

    @GetMapping("/get-all-blog")
    public ResponseEntity<List<CareerDto>> getAllBlogs() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, discoveryService.getAllBlogs());
    }

    @GetMapping("/get-all-partner-ship")
    public ResponseEntity<List<CareerDto>> getAllPartnerShips() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, discoveryService.getAllPartnerShip());
    }

    @GetMapping("/get-all-customer-support")
    public ResponseEntity<List<CareerDto>> getAllCustomerSupports() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, discoveryService.getAllCustomerSupports());
    }

    @PostMapping("/career")
    public ResponseEntity<CareerDto> add(@RequestBody CareerDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        Careers result;
        try {
            result = discoveryService.addNewCareer(dto);
            dto.setId(result.getId());
            return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_INSERT_DATA_FAILED);
        }
    }

    @PostMapping("/blog")
    public ResponseEntity<CareerDto> add(@RequestBody BlogDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getImage())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        Blogs result;
        try {
            result = discoveryService.addNewBlog(dto);
            dto.setId(result.getId());
            return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_INSERT_DATA_FAILED);
        }
    }

    @PostMapping("/partner-ship")
    public ResponseEntity<CareerDto> add(@RequestBody PartnerShipDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getImage())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getImageIcon())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        PartnerShip result;
        try {
            result = discoveryService.addNewPartnerShip(dto);
            dto.setId(result.getId());
            return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_INSERT_DATA_FAILED);
        }
    }

    @PostMapping("/customer-support")
    public ResponseEntity<CareerDto> add(@RequestBody CustomerSupportDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (CUSTOMER_SUPPORT_TITLE.indexOf(dto.getTitle()) < 0) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        CustomerSupports result;
        try {
            result = discoveryService.addNewCustomerSupport(dto);
            dto.setId(result.getId());
            return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, dto);
        } catch (Exception e) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_INSERT_DATA_FAILED);
        }
    }

    @PutMapping("/career")
    public ResponseEntity<?> update(@RequestBody CareerDto dto) {
        Careers career = discoveryService.getCareerById(dto.getId());
        if (career == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        CareerDto response = discoveryService.updateCareer(dto);
        if (response == null) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_UPDATED_DATA_FAILED);
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, response);
    }

    private boolean validatedStatus(String status) {
        return EntityStatus.ACTIVATED.toString().equals(status)
                || EntityStatus.DEACTIVATED.toString().equals(status);
    }

    @PutMapping("/blog")
    public ResponseEntity<?> update(@RequestBody BlogDto dto) {
        Blogs blogs = discoveryService.getBlogById(dto.getId());
        if (blogs == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getImage())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        BlogDto response = discoveryService.updateBlog(dto);
        if (response == null) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_UPDATED_DATA_FAILED);
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, response);
    }

    @PutMapping("/partner-ship")
    public ResponseEntity<?> update(@RequestBody PartnerShipDto dto) {
        PartnerShip partnerShip = discoveryService.getPartnerShipById(dto.getId());
        if (partnerShip == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getImage())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getImageIcon())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }

        PartnerShipDto response = discoveryService.updatePartnerShip(dto);
        if (response == null) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_UPDATED_DATA_FAILED);
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, response);
    }

    @PutMapping("/customer-support")
    public ResponseEntity<?> update(@RequestBody CustomerSupportDto dto) {
        CustomerSupports customerSupports = discoveryService.getCustomerSupportsById(dto.getId());
        if (customerSupports == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        CustomerSupportDto response = discoveryService.updateCustomerSupports(dto);
        if (response == null) {
            return ResponseUtil.internalServerError(Constants.MESSAGE_UPDATED_DATA_FAILED);
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, response);
    }

}
