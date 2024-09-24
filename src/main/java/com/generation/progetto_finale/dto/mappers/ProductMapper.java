package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.ProductDTO;
import com.generation.progetto_finale.modelEntity.Product;

@Mapper
public interface ProductMapper 
{
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.name",target = "categoryName")
    @Mapping(source = "supplier.name",target = "supplierName")
    ProductDTO toDTO(Product p);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "orders"  , ignore = true)
    Product toEntity(ProductDTO dto);
}
