package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.OrderDTO;
import com.generation.progetto_finale.modelEntity.Order;


@Mapper
public interface OrderMapper
{
    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);


    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.supplier.name", target = "supplierName")
    @Mapping(source = "product.unitType", target = "unitType ")
    @Mapping(source = "product.packagingType", target = "packagingType")
    @Mapping(source = "product.code", target = "productCode")
    OrderDTO toDTO(Order o);

    @Mapping(target = "product", ignore = true)
    Order toEntity(OrderDTO dto);

}
