package io.okkio.mybatis;

import io.okkio.dto.OrderDto;
import io.okkio.dto.response.OrderItemDtoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrderItemMybatis {
    List<OrderItemDtoResponse> getOrderItemByOrderId(@Param("ids") Long ids);
}
