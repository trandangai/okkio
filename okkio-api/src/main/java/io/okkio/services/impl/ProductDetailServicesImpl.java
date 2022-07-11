package io.okkio.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.okkio.domain.ProductDetail;
import io.okkio.dto.PDShoppingCartDto;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.response.ResponseProductDetailCategoryDto;
import io.okkio.mapper.ProductDetailMapper;
import io.okkio.mybatis.ProductDetailMybatis;
import io.okkio.repository.ProductDetailRepository;
import io.okkio.services.ProductDetailServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ProductDetailServicesImpl
 */
@Slf4j
@Service
public class ProductDetailServicesImpl extends BaseServiceImpl<ProductDetail, Long> implements ProductDetailServices {

    public ProductDetailServicesImpl(JpaRepository<ProductDetail, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductDetailMybatis productDetailMybatis;

    private ObjectMapper mapper;
    @Value("${tastingNotes}")
    private String tastingNotes;
    @Value("${readyToDrink}")
    private String readyToDrink;
    @Value("${shippingDelivery}")
    private String shippingDelivery;

    @Override
    public List<ProductDetail> getAllProductDetail() {
        return super.findAll();
    }

    @Override
    public ProductDetail addProductDetail(RequestProductDetailDto dto) {
        if (Strings.isNullOrEmpty(dto.getTastingNotes())) {
            dto.setTastingNotes(tastingNotes);
        }
        if (Strings.isNullOrEmpty(dto.getReadyToDrink())) {
            dto.setReadyToDrink(readyToDrink);
        }
        if (Strings.isNullOrEmpty(dto.getShippingDelivery())) {
            dto.setShippingDelivery(shippingDelivery);
        }
        ProductDetail util = productDetailMapper.toEntity(dto);
        util.setCreatedBy("System");
        util.setHeaderImages(String.join(",", dto.getHeaderImages()));
        return super.save(util);
    }

    @Override
    public ProductDetailDto getProductDetailById(Long id) {
        ProductDetail productDetail = productDetailRepository.findProductDetailById(id);
        if (productDetail == null) {
            log.warn("ProductDetailServicesImpl - getProductDetailById null with id: " + id);
            return null;
        }
        //Temporary to get suggestion logic
        List<ProductDetail> data = productDetailRepository.findAll();
        List<ResponseProductDetailCategoryDto> suggestions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ResponseProductDetailCategoryDto dto = productDetailMapper.toCategoryDto(data.get(i));
            dto.setHeaderImages(Stream.of(data.get(i).getHeaderImages().split(",")).collect(Collectors.toList()));
            suggestions.add(dto);
        }
        ProductDetailDto result = productDetailMapper.toDto(productDetail);
        result.setHeaderImages(Stream.of(productDetail.getHeaderImages().split(",")).collect(Collectors.toList()));
        result.setSuggestion(suggestions);
        mapper = new ObjectMapper();
        try {
            result.setTastingNotes(mapper.readValue(productDetail.getTastingNotes(), Object.class));
            result.setShippingDelivery(mapper.readValue(productDetail.getShippingDelivery(), new TypeReference<List<Object>>(){}));
        } catch (JsonProcessingException e) {
            log.warn("ProductDetailServicesImpl - JsonProcessingException with id: " + id + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public PDShoppingCartDto getProductDetailShoppingCartById(Long id) {
        ProductDetail productDetail = productDetailRepository.findProductDetailById(id);
        if (productDetail == null) {
            log.warn("ProductDetailServicesImpl - getProductDetailById null with id: " + id);
            return null;
        }
        //Temporary to get suggestion logic
        List<ProductDetail> data = productDetailRepository.findAll();
        List<ResponseProductDetailCategoryDto> suggestions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ResponseProductDetailCategoryDto dto = productDetailMapper.toCategoryDto(data.get(i));
            dto.setHeaderImages(Stream.of(data.get(i).getHeaderImages().split(",")).collect(Collectors.toList()));
            suggestions.add(dto);
        }
        PDShoppingCartDto result = productDetailMapper.toDtoSCart(productDetail);
        result.setHeaderImages(Stream.of(productDetail.getHeaderImages().split(",")).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void deleteProductDetailById(Long id) {
        productDetailRepository.deleteProductDetailById(id);
        log.warn("ProductDetailServicesImpl - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean isExistedProductDetail(String name) {
        ProductDetail util = productDetailRepository.findProductDetailByName(name);
        if (util != null) {
            return util.getName().equals(name);
        }
        return false;
    }

    @Override
    public boolean update(RequestProductDetailUpdateDto dto) {
        int isUpdated = productDetailMybatis.updateProductDetailByIds(dto.getId(), dto.getStatus(), dto.getName(), dto.getDescription(),
                dto.getHeaderImages(), dto.getFooterImages());
        if (isUpdated < 0) {
            log.warn("Can't update Util with dto: " + dto);
            return false;
        }
        return true;
    }

    @Override
    public ResponseProductDetailCategoryDto getProductDetailByCategoryId(Long id) {
        ProductDetail productDetail = productDetailRepository.findProductDetailById(id);
        if (productDetail == null) {
            log.warn("ProductDetailServicesImpl - getProductDetailById null with id: " + id);
            return null;
        }
        ResponseProductDetailCategoryDto result = productDetailMapper.toCategoryDto(productDetail);
        result.setHeaderImages(Stream.of(productDetail.getHeaderImages().split(",")).collect(Collectors.toList()));
        return result;
    }
}
