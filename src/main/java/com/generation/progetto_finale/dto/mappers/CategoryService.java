package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.CategoryDTO;
import com.generation.progetto_finale.dto.SupplierDTO;
import com.generation.progetto_finale.modelEntity.Category;
import com.generation.progetto_finale.modelEntity.Supplier;

@Service
public class CategoryService 
{

    private CategoryMapper mapper = CategoryMapper.INSTANCE;

    public Category toEntity(CategoryDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Category> toEntity(List<CategoryDTO> dtos)
    {
        List<Category> res = new ArrayList<>();

        for(CategoryDTO dto:dtos)
            res.add(mapper.toEntity(dto));

        return res;
    }

    public CategoryDTO toDTO(Category p)
    {
        return mapper.toDTO(p);
    }

    public List<CategoryDTO> toDTO(List<Category> products)
    {
        List<CategoryDTO> res = new ArrayList<>();

        for(Category p:products)
            res.add(mapper.toDTO(p));

        return res;
    }
}
