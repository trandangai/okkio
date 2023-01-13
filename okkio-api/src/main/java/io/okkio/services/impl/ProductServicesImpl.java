package io.okkio.services.impl;

import io.okkio.domain.Product;
import io.okkio.dto.ProductDto;
import io.okkio.dto.request.RequestProductDto;
import io.okkio.mapper.ProductMapper;
import io.okkio.mybatis.ProductMybatis;
import io.okkio.repository.ProductRepository;
import io.okkio.services.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductServicesImpl
 */
@Slf4j
@Service
public class ProductServicesImpl extends BaseServiceImpl<Product, Long> implements ProductServices {

    public ProductServicesImpl(JpaRepository<Product, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductMybatis productMybatis;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findProductByStatusActivated("ACTIVATED");
    }

    @Override
    public Product addProduct(RequestProductDto dto) {
        Product product = productMapper.toEntity(dto);
        product.setCreatedBy("System");
        return super.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteProductById(id);
        log.warn("ProductServicesImpl - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean isExistedProduct(String name) {
        Product util = productRepository.findProductByName(name);
        if (util != null) {
            return util.getName().equals(name);
        }
        return false;
    }

    @Override
    public boolean update(RequestProductDto dto) {
        int isUpdated = productMybatis.updateProductByIds(dto.getId(), dto.getStatus(), dto.getName(),
                dto.getProductDetailId(), dto.getCategoryId());
        if (isUpdated < 0) {
            log.warn("Can't update Util with dto: " + dto);
            return false;
        }
        return true;
    }

    @Override
    public List<Product> getProductByCategoryId(Long id) {
        return productRepository.findProductByCategoryId(id);
    }
}
