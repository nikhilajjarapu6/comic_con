package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.OrderRequestDto;
import com.comiccon.dto.OrderResponseDto;
import com.comiccon.entity.Orders;

@Mapper(componentModel = "spring",
uses = { ItemOrderMapper.class, UserMapper.class, AddressMapper.class }
)
public interface OrderMapper {

	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "addressId", target = "address.id")
	Orders toEntity(OrderRequestDto dto);
	
	@Mapping(source = "id", target = "id") // not really needed; MapStruct maps same-name fields
	OrderResponseDto toDto(Orders order);
	
	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "addressId", target = "address.id")
	@Mapping(target = "id", ignore = true)
	void updateFromDto(OrderRequestDto dto, @MappingTarget Orders order);
}

