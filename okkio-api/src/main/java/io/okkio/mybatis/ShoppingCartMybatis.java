package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface ShoppingCartMybatis {
    int updateShoppingCartByIds(@Param("ids") Long ids, @Param("status") String status, @Param("updateBy") String updateBy,
                                @Param("productDetailId") Long productDetailId);
}
