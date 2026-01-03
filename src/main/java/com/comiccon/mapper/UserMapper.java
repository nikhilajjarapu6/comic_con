package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.comiccon.dto.UserRequestDto;
import com.comiccon.dto.UserResponseDto;
import com.comiccon.entity.User;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
	User toEntity(UserRequestDto dto);
	UserResponseDto toDto(User user);
	
	//mapping target tells spring do not create new object instead target that object
	@Mapping(target = "id", ignore = true)
	void updateFromDto(UserRequestDto dto, @MappingTarget User user);
}
