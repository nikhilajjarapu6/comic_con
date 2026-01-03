package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.ComicRequestDto;
import com.comiccon.dto.ComicResponseDto;
import com.comiccon.entity.Comic;

@Mapper(componentModel = "spring")
public interface ComicMapper {

    // Don't map genreId here — handle in service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", ignore = true)
    Comic toEntity(ComicRequestDto dto);

    // Map genre name (String) to genre field in response DTO
    @Mapping(source = "genre.genre", target = "genre")
    ComicResponseDto toDto(Comic comic);

    // For update, also ignore genre mapping
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", ignore = true)
    void updateFromDto(ComicRequestDto dto, @MappingTarget Comic comic);
}