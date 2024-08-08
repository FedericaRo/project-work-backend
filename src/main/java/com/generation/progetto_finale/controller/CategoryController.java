package com.generation.progetto_finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.CategoryDTO;
import com.generation.progetto_finale.dto.mappers.CategoryService;
import com.generation.progetto_finale.repositories.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController 
{
    @Autowired
    CategoryRepository cRepo;
    @Autowired
    CategoryService cServ;

    @GetMapping
    public List<CategoryDTO> getAll()
    {
        return cServ.toDTO(cRepo.findAll());
    }

}
