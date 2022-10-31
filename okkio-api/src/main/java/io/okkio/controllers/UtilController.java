package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.Categories;
import io.okkio.domain.Util;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.UtilDto;
import io.okkio.dto.request.RequestCategoryDto;
import io.okkio.dto.request.RequestUtilDto;
import io.okkio.services.CategoryServices;
import io.okkio.services.UtilServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utils")
public class UtilController {

    private UtilServices utilServices;

    public UtilController(UtilServices utilServices) {
        this.utilServices = utilServices;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<Util> add(@RequestBody RequestUtilDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        }
        Util result = utilServices.addUtil(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, result);
    }

    @PreAuthorize("hasAnyRole('OKKIO_USER', 'OKKIO_GUEST', 'OKKIO_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Util>> getAllUtils() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, utilServices.getAllUtil());
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestUtilDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            Util util = utilServices.getUtilById(dto.getId());
            if (util == null) {
                return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
            }
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_DATA_SUCCESS, utilServices.update(dto));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<List<Util>> delete(@Param("id") Long id) {
        Util util = utilServices.getUtilById(id);
        if (util == null) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        utilServices.deleteUtilById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<List<Util>> getUtilByName(@Param("name") String name) {
        List<Util> result = utilServices.getUtilByName(name.toUpperCase());
        if (result == null || result.isEmpty()) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED, null);
        }
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, result);
    }

    @GetMapping("/init")
    public ResponseEntity<UtilDto> initUtils() {
        UtilDto result = utilServices.initUtil();
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
