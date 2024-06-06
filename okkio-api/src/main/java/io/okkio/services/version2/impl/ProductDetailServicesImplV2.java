package io.okkio.services.version2.impl;

import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.request.version2.RequestProductDetailDto;
import io.okkio.dto.response.version2.ResponseProductDetailDto;
import io.okkio.dto.version2.ProductDetailDto;
import io.okkio.mapper.version2.ProductDetailMapperV2;
import io.okkio.mybatis.ProductDetailMybatis;
import io.okkio.repository.version2.ProductDetailRepositoryV2;
import io.okkio.services.impl.BaseServiceImpl;
import io.okkio.services.version2.ProductDetailServicesV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ProductDetailServicesImplV2
 */
@Slf4j
@Service
public class ProductDetailServicesImplV2 extends BaseServiceImpl<ProductDetailV2, Long> implements ProductDetailServicesV2 {

    public ProductDetailServicesImplV2(JpaRepository<ProductDetailV2, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ProductDetailRepositoryV2 productDetailRepository;

    @Autowired
    private ProductDetailMapperV2 productDetailMapper;

    @Autowired
    private ProductDetailMybatis productDetailMybatis;

    @Override
    public List<ProductDetailDto> getAllProductDetail() {
        List<ProductDetailV2> productDetails = productDetailRepository.findProductDetailsByStatusActivated("ACTIVATED");
        List<ProductDetailDto> result = new ArrayList<>();
        if (productDetails == null || productDetails.isEmpty()) {
            log.warn("getAllProductDetail is null ");
            return null;
        }
        for (ProductDetailV2 productDetail: productDetails) {
            ProductDetailDto productDetailDto = productDetailMapper.toDto(productDetail);
            productDetailDto.setProductImages(Stream.of(productDetail.getProductImages().split(",")).collect(Collectors.toList()));
            result.add(productDetailDto);
        }
        return result;
    }

    @Override
    public ProductDetailV2 addProductDetail(RequestProductDetailDto dto) {
        ProductDetailV2 util = productDetailMapper.toEntity(dto);
        util.setCreatedBy(dto.getEmail());
        util.setProductImages(String.join(",", dto.getProductImages()));
        util.setSlug(dto.getName().toLowerCase().replace(" ", "-"));
        return super.save(util);
    }

    @Override
    public ProductDetailDto getProductDetailById(Long id) {
        ProductDetailV2 productDetailV2 = productDetailRepository.findProductDetailById(id);
        return productDetailMapper.toDto(productDetailV2);
    }

    @Override
    public ResponseProductDetailDto getProductDetailBySlug(String slug) {
        ProductDetailV2 productDetailV2 = productDetailRepository.findProductDetailV2BySlug(slug);
        ResponseProductDetailDto result = productDetailMapper.toDtoResponseProductDetailDto(productDetailV2);
        result.setProductImages(Stream.of(productDetailV2.getProductImages().split(",")).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void deleteProductDetailById(Long id) {
        productDetailRepository.deleteProductDetailById(id);
        log.warn("ProductDetailServicesImplV2 - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean isExistedProductDetail(String name) {
        ProductDetailV2 util = productDetailRepository.findProductDetailByName(name);
        if (!ObjectUtils.isEmpty(util)) {
            return util.getName().equals(name);
        }
        return false;
    }

    @Override
    public boolean update(RequestProductDetailUpdateDto dto) {
        String headerImages = null;
        if (dto.getHeaderImages() != null) {
            headerImages = String.join(",", dto.getHeaderImages());
        }
        int isUpdated = productDetailMybatis.updateProductDetailByIds(dto.getId(), dto.getStatus(), dto.getName(), dto.getDescription(),
                headerImages, dto.getFooterImages(), dto.getGrind(), dto.getSize(), dto.getSubscription(), dto.getQuantity(), dto.getRoastLevel(),
                dto.getReadyToDrink(), dto.getSuitableFor(), dto.getPrice());
        if (isUpdated < 0) {
            log.warn("Can't update Util with dto: " + dto);
            return false;
        }
        return true;
    }

    @Override
    public List<ProductDetailV2> getProductDetailByProductId(Long id) {
        return productDetailRepository.findProductDetailsByStatusActivatedAndProductId("ACTIVATED",id);
    }
}
