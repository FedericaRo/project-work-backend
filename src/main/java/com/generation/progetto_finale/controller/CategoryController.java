package com.generation.progetto_finale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.CategoryDTO;
import com.generation.progetto_finale.dto.ProductDTO;
import com.generation.progetto_finale.dto.mappers.CategoryService;
import com.generation.progetto_finale.modelEntity.Category;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.repositories.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

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

    @PostMapping("/addCategory")
    public CategoryDTO addCategory(@RequestBody CategoryDTO category) {
        
        if (category.getName() != null && category.getName().isEmpty())
            throw new IllegalArgumentException("La categoria non può essere vuota");
        
        if (cRepo.findByName(category.getName()) != null)
            throw new IllegalArgumentException("La categoria è già presente");
        
        Category newCategory = cRepo.save(cServ.toEntity(category));

        return cServ.toDTO(newCategory);
    }

    @DeleteMapping("{categoryId}")
    public Integer deleteCategory(@PathVariable Integer categoryId)
    {
        System.out.println(categoryId);
        Optional<Category> categoryToDelete = cRepo.findById(categoryId);
        if (categoryToDelete.isEmpty()) 
            throw new EntityNotFoundException("La categoria non esiste");

        cRepo.delete(categoryToDelete.get());

        return categoryId;
    }

}
