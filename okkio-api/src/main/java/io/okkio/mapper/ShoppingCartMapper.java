package io.okkio.mapper;

import io.okkio.domain.ShoppingCart;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.request.RequestShoppingCartDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ShoppingCartMapper {
    public abstract ShoppingCart toEntity(ShoppingCartDto dto);
    public abstract ShoppingCart toEntityRequestShoppingCartDto(RequestShoppingCartDto dto);
    public abstract List<ShoppingCart> toEntityRequestShoppingCartDTOs(List<RequestShoppingCartDto> DTOs);
}
