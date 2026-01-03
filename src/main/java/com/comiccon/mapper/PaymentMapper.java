package com.comiccon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.comiccon.dto.PaymentRequestDto;
import com.comiccon.dto.PaymentResponseDto;
import com.comiccon.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "orderId", target = "order.id") // from DTO to entity
    Payment toEntity(PaymentRequestDto dto);

    @Mapping(source = "order.id", target = "orderId") // from entity to DTO
    PaymentResponseDto toDto(Payment payment);

    @Mapping(source = "orderId", target = "order.id") // fix: map orderId properly
    @Mapping(target = "id", ignore = true)
    void updateFromDto(PaymentRequestDto dto, @MappingTarget Payment payment);
}
