package io.okkio.services.version2.impl;

import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import io.okkio.mapper.version2.ProductMapperV2;
import io.okkio.mybatis.ProductMybatis;
import io.okkio.repository.version2.ProductRepositoryV2;
import io.okkio.services.impl.BaseServiceImpl;
import io.okkio.services.version2.ProductServicesV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductServicesImplV2
 */
@Slf4j
@Service
public class ProductServicesImplV2 extends BaseServiceImpl<ProductV2, Long> implements ProductServicesV2 {

    public ProductServicesImplV2(JpaRepository<ProductV2, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ProductRepositoryV2 productRepository;

    @Autowired
    private ProductMapperV2 productMapper;

    @Autowired
    private ProductMybatis productMybatis;

    @Override
    public List<ProductV2> getAllProduct() {
        return productRepository.findProductByStatusActivated("ACTIVATED");
    }

    @Override
    public ProductV2 addProduct(RequestProductDto dto) {
        ProductV2 product = productMapper.toEntity(dto);
        product.setCreatedBy(dto.getEmail());
        product.setSlug(dto.getName().toLowerCase().replace(" ", "-"));
        return super.save(product);
    }

    @Override
    public ProductV2 getProductById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public ProductV2 getProductBySlug(String slug) {
        return productRepository.findProductV2BySlug(slug);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteProductById(id);
        log.warn("ProductServicesImplV2 - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean isExistedProduct(String name) {
        ProductV2 util = productRepository.findProductByName(name);
        if (util != null) {
            return util.getName().equals(name);
        }
        return false;
    }

    @Override
    public boolean update(RequestProductDto dto) {
        int isUpdated = productMybatis.updateProductByIds(dto.getId(), dto.getStatus(), dto.getName(), dto.getCategoryId());
        if (isUpdated < 0) {
            log.warn("Can't update Util with dto: " + dto);
            return false;
        }
        return true;
    }

    @Override
    public List<ProductV2> getProductByCategoryId(Long id) {
        return productRepository.findProductByCategoriesV2Id(id);
    }
}
