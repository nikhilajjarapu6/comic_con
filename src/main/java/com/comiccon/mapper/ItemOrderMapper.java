package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.ItemOrderRequestDto;
import com.comiccon.dto.ItemOrderResponseDto;
import com.comiccon.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface ItemOrderMapper {

    @Mapping(source = "comicId", target = "comic.id") // ✅ fix added
    OrderItem toEntity(ItemOrderRequestDto dto);

    @Mapping(source = "comic.title", target = "title")
    @Mapping(source = "comic.author", target = "author")
    @Mapping(source = "comic.genre.genre", target = "genre")
    ItemOrderResponseDto toDto(OrderItem item);

    @Mapping(source = "comicId", target = "comic.id") // ✅ fix added
    @Mapping(target = "id", ignore = true)
    void updateFromDto(ItemOrderRequestDto dto, @MappingTarget OrderItem item);
}

