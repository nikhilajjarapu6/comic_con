package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.CartItemRequestDto;
import com.comiccon.dto.CartItemResponseDto;
import com.comiccon.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    // Convert DTO to Entity
    @Mapping(target = "comic", ignore = true) // set in service
    @Mapping(target = "cart", ignore = true)  // set in service
    @Mapping(target = "id", ignore = true)    // DB generated
    CartItem toEntity(CartItemRequestDto dto);

    // Convert Entity to Response DTO
    @Mapping(source = "comic.title", target = "comicTitle")
    @Mapping(source = "cart.id", target = "cartId")
    CartItemResponseDto toDto(CartItem cartItem);

    // Update existing entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "comic", ignore = true) // 🚨 IMPORTANT
    void updateFromDto(CartItemRequestDto dto,
                       @MappingTarget CartItem cartItem);
}
