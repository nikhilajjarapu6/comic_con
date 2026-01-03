package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.comiccon.dto.AddressRequestDto;
import com.comiccon.dto.AddressResponseDto;
import com.comiccon.entity.Address;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

//    @Mapping(source = "userId", target = "user.id") 
    Address toEntity(AddressRequestDto dto);

    @Mapping(source = "user.username", target = "username")
    AddressResponseDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateFromDto(AddressRequestDto dto, @MappingTarget Address address);
}
