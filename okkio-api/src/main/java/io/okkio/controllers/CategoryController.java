package io.okkio.controllers;

import io.okkio.common.Constants;
import io.okkio.domain.Categories;
import io.okkio.dto.UserDto;
import io.okkio.dto.request.RequestCategoryDto;
import io.okkio.dto.response.ResponseCategoryDto;
import io.okkio.dto.response.UserDtoResponse;
import io.okkio.mybatis.UserMybatis;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.CategoryServices;
import io.okkio.services.UserServices;
import io.okkio.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryServices categoryServices;

    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<Categories> add(@RequestBody RequestCategoryDto dto) {
        boolean isExistedByCategory;
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByCategory = categoryServices.isExistedCategory(dto.getName());
        }
        if (isExistedByCategory) {
            return ResponseUtil.ok(Constants.MESSAGE_USER_IS_EXISTED, null);
        }
        Categories result = categoryServices.addCategories(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Categories>> getAllCategories() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, categoryServices.getAllCategories());
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestCategoryDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            Categories categories = categoryServices.getCategoriesById(dto.getId());
            if (categories == null) {
                return ResponseUtil.ok(Constants.MESSAGE_CATEGORY_IS_NOT_EXISTED, null);
            }
        }
        if (StringUtils.isEmpty(dto.getStatus())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            if (!validatedStatus(dto.getStatus())) {
                return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
            }
        }
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_CATEGORY_SUCCESS, categoryServices.update(dto));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<List<Categories>> delete(@Param("id") Long id) {
        Categories categories = categoryServices.getCategoriesById(id);
        if (categories == null) {
            return ResponseUtil.ok(Constants.MESSAGE_CATEGORY_IS_NOT_EXISTED, null);
        }
        categoryServices.deleteCategoryById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

    private boolean validatedStatus(String status) {
        return Constants.ACTIVATED_STATUS.equals(status)
                || Constants.DEACTIVATED_STATUS.equals(status);
    }
}
