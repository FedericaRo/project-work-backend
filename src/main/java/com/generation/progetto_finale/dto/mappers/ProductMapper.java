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

    //qui mettiamo tutto quello che di base non c'è nel Product
    // @Mapping(source = "category.id",target = "category_id")
    // @Mapping(source = "supplier.id",target = "supplier_id")
    @Mapping(source = "category.name",target = "categoryName")
    @Mapping(source = "supplier.name",target = "supplierName")
    @Mapping(source = "supplier.code",target = "supplierCode")
    ProductDTO toDTO(Product p);

    //qui trasformiamo da DTO a Product, ignorando category e supplier che
    //verranno messe dal Controller
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "orders"  , ignore = true)
    Product toEntity(ProductDTO dto);
}
