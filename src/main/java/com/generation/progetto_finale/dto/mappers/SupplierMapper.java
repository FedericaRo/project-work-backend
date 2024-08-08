package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.SupplierDTO;
import com.generation.progetto_finale.modelEntity.Supplier;

@Mapper
public interface SupplierMapper 
{
    public static final SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDTO toDTO(Supplier c);

    @Mapping(target = "products"  , ignore = true)
    Supplier toEntity(SupplierDTO dto);
}
