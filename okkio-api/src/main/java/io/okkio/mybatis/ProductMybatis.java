package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface ProductMybatis {
    int updateProductByIds(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name,
                        @Param("productDetailId") Long productDetailId, @Param("categoryId") Long categoryId);
}
