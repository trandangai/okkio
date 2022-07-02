package io.okkio.services.impl;

import io.okkio.domain.Categories;
import io.okkio.dto.request.RequestCategoryDto;
import io.okkio.mapper.CategoryMapper;
import io.okkio.mybatis.CategoryMybatis;
import io.okkio.repository.CategoryRepository;
import io.okkio.services.CategoryServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CategoryServicesImpl
 */
@Slf4j
@Service
public class CategoryServicesImpl extends BaseServiceImpl<Categories, Long> implements CategoryServices {

    public CategoryServicesImpl(JpaRepository<Categories, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMybatis categoryMybatis;

    @Override
    public List<Categories> getAllCategories() {
        return super.findAll();
    }

    @Override
    public Categories addCategories(RequestCategoryDto dto) {
        Categories categories = categoryMapper.toEntity(dto);
        categories.setCreatedBy("System");
        return super.save(categories);
    }

    @Override
    public Categories getCategoriesById(Long id) {
        return categoryRepository.findCategoriesById(id);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteCategoriesById(id);
        log.warn("CategoryServicesImpl - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean isExistedCategory(String name) {
        Categories categories = categoryRepository.findCategoriesByName(name);
        if (categories != null) {
            return categories.getName().equals(name);
        }
        return false;
    }

    @Override
    public boolean update(RequestCategoryDto dto) {
        int isUpdated = categoryMybatis.updateCategoryByIds(dto.getId(), dto.getStatus(), dto.getName());
        if (isUpdated < 0) {
            log.warn("Can't update Category with dto: " + dto);
            return false;
        }
        return true;
    }
}
