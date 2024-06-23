package io.okkio.controllers.version2;

import io.okkio.common.Constants;
import io.okkio.domain.version2.CategoriesV2;
import io.okkio.dto.request.version2.RequestCategoryDto;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.version2.CategoriesServices;
import io.okkio.util.ObjectUtil;
import io.okkio.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static io.okkio.util.ValidationUtil.validatedStatus;

@RestController
@RequestMapping("/api/v2/categories")
@Slf4j
public class CategoriesController {

    @Autowired
    private CategoriesServices categoriesServices;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoriesV2> add(@RequestBody RequestCategoryDto dto, HttpServletRequest httpServletRequest) {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        boolean isExistedByCategory;
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            isExistedByCategory = categoriesServices.isExistedCategory(dto.getName());
        }
        if (isExistedByCategory) {
            return ResponseUtil.ok(Constants.MESSAGE_CATEGORY_IS_EXISTED, "CATEGORY_IS_EXISTED");
        }
        dto.setEmail(email);
        CategoriesV2 result = categoriesServices.addCategories(dto);
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_USER_SUCCESS, result);
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @GetMapping("/get-all-by-admin")
    public ResponseEntity<List<CategoriesV2>> getAllCategoriesByAdmin() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, categoriesServices.getAllCategoriesByAdmin());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoriesV2>> getAllCategories() {
        return ResponseUtil.ok(Constants.MESSAGE_GET_DATA_SUCCESS, categoriesServices.getAllCategories());
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestCategoryDto dto) {
        if (StringUtils.isEmpty(dto.getName())) {
            return ResponseUtil.badRequest(Constants.MESSAGE_BAD_REQUEST);
        } else {
            CategoriesV2 categoriesV2 = categoriesServices.getCategoriesById(dto.getId());
            if (ObjectUtil.isEmpty(categoriesV2)) {
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
        return ResponseUtil.ok(Constants.MESSAGE_UPDATED_CATEGORY_SUCCESS, categoriesServices.update(dto));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<List<CategoriesV2>> delete(@Param("id") Long id) {
        CategoriesV2 categoriesV2 = categoriesServices.getCategoriesById(id);
        if (categoriesV2 == null) {
            return ResponseUtil.ok(Constants.MESSAGE_CATEGORY_IS_NOT_EXISTED, null);
        }
        categoriesServices.deleteCategoryById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }

}
