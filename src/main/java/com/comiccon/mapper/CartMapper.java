package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.CartRequestDto;
import com.comiccon.dto.CartResponseDto;
import com.comiccon.entity.Cart;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class })
public interface CartMapper {
	@Mapping(source = "userId",target = "user.id")
    Cart toEntity(CartRequestDto dto);

    @Mapping(source = "user.username", target = "username")
    CartResponseDto toDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateFromDto(CartRequestDto dto, @MappingTarget Cart cart);
}
