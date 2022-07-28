package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface ShipmentMybatis {
    int updateShipmentByIds(@Param("ids") Long ids, @Param("status") String status, @Param("detail") String detail,
                        @Param("type") String type, @Param("receiptId") Long receiptId, @Param("orderId") Long orderId,
                        @Param("updatedBy") String updatedBy);
}
