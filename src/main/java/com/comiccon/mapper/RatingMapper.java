package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.RatingRequestDto;
import com.comiccon.dto.RatingResponseDto;
import com.comiccon.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "comicId", target = "comic.id") // ✅ fix
    Rating toEntity(RatingRequestDto dto);

    @Mapping(source = "comic.title", target = "title")
    RatingResponseDto toDto(Rating rating);

    @Mapping(source = "comicId", target = "comic.id") // ✅ fix for updates too
    @Mapping(target = "id", ignore = true)
    void updateFromDto(RatingRequestDto dto, @MappingTarget Rating rating);
}

