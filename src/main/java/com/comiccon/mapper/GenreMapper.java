package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.GenreRequestDto;
import com.comiccon.dto.GenreResponseDto;
import com.comiccon.entity.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {
	Genre toEntity(GenreRequestDto dto);
	
	GenreResponseDto toDto(Genre genre);
	
	@Mapping(target = "id", ignore = true)
	void updateFromDto(GenreRequestDto dto, @MappingTarget Genre genre);
	
}
