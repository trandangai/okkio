package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


public interface ProductDetailMybatis {
    int updateProductDetailByIds(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name,
                                 @Param("description") String description, @Param("headerImages") String headerImages,
                                 @Param("footerImages") String footerImages, @Param("grind") String grind, @Param("size") String size,
                                 @Param("subscription") String subscription, @Param("quantity") int quantity, @Param("roastLevel") int roastLevel,
                                 @Param("readyToDrink") String readyToDrink, @Param("suitableFor") String suitableFor, @Param("price") BigDecimal price);
}
