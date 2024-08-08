package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.CategoryDTO;
import com.generation.progetto_finale.modelEntity.Category;

@Mapper
public interface CategoryMapper 
{
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    
    CategoryDTO toDTO(Category c);

    @Mapping(target = "products"  , ignore = true)
    Category toEntity(CategoryDTO dto);
}
