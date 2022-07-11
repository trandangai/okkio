package io.okkio.mapper;

import io.okkio.domain.ShoppingCart;
import io.okkio.dto.request.RequestShoppingCartDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ShoppingCartMapper {
    public abstract ShoppingCart toEntity(RequestShoppingCartDto dto);
}
