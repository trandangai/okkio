package io.okkio.services;

import io.okkio.domain.Categories;
import io.okkio.dto.request.RequestCategoryDto;

import java.util.List;

public interface CategoryServices {
    List<Categories> getAllCategories();
    Categories addCategories(RequestCategoryDto dto);
    Categories getCategoriesById(Long id);
    void deleteCategoryById(Long id);
    boolean isExistedCategory(String name);
    boolean update(RequestCategoryDto dto);
}
