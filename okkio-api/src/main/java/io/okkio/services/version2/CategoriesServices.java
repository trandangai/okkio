package io.okkio.services.version2;

import io.okkio.domain.version2.CategoriesV2;
import io.okkio.dto.request.version2.RequestCategoryDto;
import io.okkio.dto.version2.CategoriesDto;

import java.util.List;

public interface CategoriesServices {
    List<CategoriesV2> getAllCategoriesByAdmin();
    CategoriesV2 addCategories(RequestCategoryDto dto);
    CategoriesV2 getCategoriesById(Long id);
    void deleteCategoryById(Long id);
    boolean isExistedCategory(String name);
    boolean update(RequestCategoryDto dto);
    List<CategoriesDto> getAllCategories();
}
