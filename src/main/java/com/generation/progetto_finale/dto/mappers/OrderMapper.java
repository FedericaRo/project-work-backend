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

    //qui mettiamo tutto quello che di base non c'è nel order

    // @Mapping(source = "product.id", target = "product_id")
    @Mapping(source = "product.productName", target = "productName")
    // @Mapping(source = "product.category.id", target = "category_id")
    // @Mapping(source = "product.category.name", target = "categoryName")
    // @Mapping(source = "product.supplier.id", target = "supplier_id")
    @Mapping(source = "product.supplier.name", target = "supplierName")
    @Mapping(source = "product.supplier.code", target = "supplierCode")
    // @Mapping(source = "product.packagingTypeQuantity", target = "packagingTypeQuantity")
    // @Mapping(source = "product.unitTypeQuantity", target = "unitTypeQuantity")
    @Mapping(source = "product.unitType", target = "unitType ")
    // @Mapping(source = "product.unitPrice", target = "unitPrice")
    // @Mapping(source = "product.unitsPerPackaging", target = "unitsPerPackaging")
    @Mapping(source = "product.packagingType", target = "packagingType")
    // @Mapping(target = "packagingType", ignore = true)
    // @Mapping(target = "packagingTypeQuantity", ignore = true)
    // @Mapping(target = "unitPrice", ignore = true)
    // @Mapping(target = "unitType", ignore = true)
    // @Mapping(target = "unitTypeQuantity", ignore = true)
    // @Mapping(target = "unitsPerPackaging", ignore = true)
    OrderDTO toDTO(Order o);

    //qui trasformiamo da DTO a Product, ignorando category e supplier che
    //verranno messe dal Controller
    @Mapping(target = "product", ignore = true)
    // @Mapping(target = "orderDate", ignore = true)
    // @Mapping(target = "deliverDate", ignore = true)
    Order toEntity(OrderDTO dto);

}
