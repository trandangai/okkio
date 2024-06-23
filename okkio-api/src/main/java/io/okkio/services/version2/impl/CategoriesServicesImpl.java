package io.okkio.services.version2.impl;

import io.okkio.domain.version2.CategoriesV2;
import io.okkio.dto.request.version2.RequestCategoryDto;
import io.okkio.dto.version2.CategoriesDto;
import io.okkio.mapper.version2.CategoriesMapper;
import io.okkio.mybatis.CategoryMybatis;
import io.okkio.repository.version2.CategoriesRepository;
import io.okkio.services.version2.CategoriesServices;
import io.okkio.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CategoryServicesImpl version 2
 */
@Slf4j
@Service
public class CategoriesServicesImpl extends BaseServiceImpl<CategoriesV2, Long> implements CategoriesServices {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private CategoryMybatis categoryMybatis;

    public CategoriesServicesImpl(JpaRepository<CategoriesV2, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public List<CategoriesV2> getAllCategoriesByAdmin() {
        return super.findAll();
    }

    @Override
    public List<CategoriesDto> getAllCategories() {
        return categoriesMapper.toListCategoriesDto(categoriesRepository.findCategoriesWithStatusActive());
    }

    @Override
    public CategoriesV2 addCategories(RequestCategoryDto dto) {
        if (StringUtil.isEmpty(dto.getStatus())) {
            dto.setStatus("ACTIVATED");
        }
        CategoriesV2 categoriesV2 = categoriesMapper.toEntity(dto);
        categoriesV2.setCreatedBy(dto.getEmail());
        return super.save(categoriesV2);
    }

    @Override
    public CategoriesV2 getCategoriesById(Long id) {
        return categoriesRepository.findCategoriesById(id);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoriesRepository.deleteCategoriesById(id);
        log.warn("CategoryServicesImpl - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean isExistedCategory(String name) {
        CategoriesV2 categoriesV2 = categoriesRepository.findCategoriesByName(name);
        if (categoriesV2 != null) {
            return categoriesV2.getName().equals(name);
        }
        return false;
    }

    @Override
    public boolean update(RequestCategoryDto dto) {
        int isUpdated = categoryMybatis.updateCategoryByIdsV2(dto.getId(), dto.getStatus(), dto.getName(), dto.getPriority());
        if (isUpdated < 0) {
            log.warn("Can't update Category with dto: " + dto);
            return false;
        }
        return true;
    }
}
