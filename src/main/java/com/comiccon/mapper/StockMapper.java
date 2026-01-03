package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.StockRequestDto;
import com.comiccon.dto.StockResponseDto;
import com.comiccon.entity.Stock;

@Mapper(componentModel = "spring")
public interface StockMapper {

	
//	@Mapping(source = "comic.genre", target = "genre")
//    @Mapping(source = "comicId", target = "comic.id")
    Stock toEntity(StockRequestDto dto);

	@Mapping(source = "comic.id", target = "comicId")
	@Mapping(source = "comic.title", target = "comicTitle")
    @Mapping(source = "comic.genre", target = "genre") // assuming this is what you want
    StockResponseDto toDto(Stock stock);

    @Mapping(source = "comicId", target = "comic.id")
    @Mapping(target = "id", ignore = true)
    void updateFromDto(StockRequestDto dto, @MappingTarget Stock stock);
}

