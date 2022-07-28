package io.okkio.mybatis;

import io.okkio.dto.OrderDto;
import org.apache.ibatis.annotations.Param;


public interface OrderMybatis {
    int updateShipmentByIds(@Param("ids") Long ids, @Param("status") String status, @Param("detail") String detail,
                        @Param("type") String type, @Param("receiptId") Long receiptId, @Param("orderId") Long orderId,
                        @Param("updatedBy") String updatedBy);
    OrderDto getOrderDto(@Param("ids") Long ids);
}
