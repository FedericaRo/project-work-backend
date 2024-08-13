package com.generation.progetto_finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.SupplierDTO;
import com.generation.progetto_finale.dto.mappers.SupplierService;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.repositories.SupplierRepository;


@RestController
@RequestMapping("/suppliers")
public class SupplierController 
{

    @Autowired
    SupplierRepository sRepo;
    @Autowired
    SupplierService sServ;

    @GetMapping
    public List<SupplierDTO> getAll()
    {
        return sServ.toDTO(sRepo.findAll());
    }

    @PostMapping("/addSupplier")
    public SupplierDTO addSupplier(@RequestBody SupplierDTO supplier) {
        
        if (supplier.getName() != null && supplier.getName().isEmpty())
            throw new IllegalArgumentException("Il nome del fornitore non può essere vuoto");
        
        if (sRepo.findByName(supplier.getName()) != null)
            throw new IllegalArgumentException("Il nome del fornitore è già presente");

        
        Supplier newSupplier = sRepo.save(sServ.toEntity(supplier));

        return sServ.toDTO(newSupplier);
    }
    


    
    
}
