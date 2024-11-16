package io.okkio.services.version2.impl;

import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import io.okkio.dto.response.version2.ProductDtoPagingResponse;
import io.okkio.dto.response.version2.ResponseProductDto;
import io.okkio.dto.response.version2.ResponseProductSlugDto;
import io.okkio.mapper.version2.ProductMapperV2;
import io.okkio.mybatis.ProductMybatis;
import io.okkio.repository.version2.ProductRepositoryV2;
import io.okkio.services.ProductDetailServices;
import io.okkio.services.impl.BaseServiceImpl;
import io.okkio.services.version2.ProductServicesV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ProductDtoPagingResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String keyword) {
        log.info("Start ProductServicesImplV2 - getAllProduct with keyword: {}", keyword);
        keyword = "%" + keyword + "%";
        ProductDtoPagingResponse result = new ProductDtoPagingResponse();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<ProductV2> products = productRepository.findProductByStatusActivated(paging);
        if (products.isEmpty()) {
            log.error("getAllProduct error no product found");
            return null;
        }
        result.setSize(products.getSize());
        result.setTotalElements(products.getTotalElements());
        result.setTotalPages(products.getTotalPages());
        result.setLast(products.isLast());
        result.setNumber(products.getNumber());
        result.setNumberOfElements(products.getNumberOfElements());
        List<ResponseProductDto> productDTOs = productMapper.toDTOs(products.getContent());
        for(ResponseProductDto dto : productDTOs) {
            dto.setProductV2s(productRepository.findProductV2ByLevelCodeAndKeyword(dto.getSlug(), keyword));
        }
        result.setContent(productDTOs);
        log.info("End ProductServicesImplV2 - getAllProduct with keyword: {}", keyword);
        return result;
    }

    @Override
    public List<ResponseProductSlugDto> getProductByAllProductSlug(String slug) {
        List<ResponseProductSlugDto> result;
        List<ProductV2> products = productRepository.findProductByStatusActivatedWithoutPaging("ACTIVATED", 0);
        products.removeIf(productV2 -> productV2.getSlug().equals(slug));
        result = productMapper.toDTOResponseProductSlugDTOs(products);
        for(ResponseProductSlugDto dto : result) {
            dto.setProductV2s(productRepository.findProductV2ByLevelCode(dto.getSlug()));
        }
        return result;
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
    public ResponseProductSlugDto getProductBySlug(String slug) {
        ResponseProductSlugDto result;
        ProductV2 productV2 = productRepository.findProductV2BySlug(slug);
        result = productMapper.toProductDTOs(productV2);
        if (productV2.getLevel() == 0) {
            result.setProductV2s(productRepository.findProductV2ByLevelCode(productV2.getSlug()));
        }
        return result;
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
        int isUpdated = productMybatis.updateProductByIds(dto.getId(), dto.getStatus(), dto.getName());
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
