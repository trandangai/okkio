package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface ProductDetailMybatis {
    int updateProductDetailByIds(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name,
                        @Param("description") String description, @Param("headerImages") String headerImages,
                        @Param("footerImages") String footerImages);
}
