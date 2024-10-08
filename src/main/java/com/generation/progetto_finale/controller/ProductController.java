package com.generation.progetto_finale.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.ProductDTO;
import com.generation.progetto_finale.dto.mappers.ProductService;
import com.generation.progetto_finale.modelEntity.Category;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.repositories.CategoryRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.repositories.SupplierRepository;

import jakarta.persistence.EntityNotFoundException;



@RestController
@RequestMapping("/products")
public class ProductController 
{

    @Autowired
    ProductRepository pRepo;
    @Autowired
    ProductService pServ;
    @Autowired
    SupplierRepository sRepo;
    @Autowired
    CategoryRepository cRepo;

    @GetMapping
    public List<ProductDTO> getAll()
    {
        return pServ.toDTO(pRepo.findAll());
    }

    @PostMapping("/newProduct")
    public ProductDTO addNewProduct(@RequestBody ProductDTO dto) {
        
        validateProductDTO(dto);

        System.out.println(dto);
        Product product = pServ.toEntity(dto);
        Supplier supplier = sRepo.findByName(dto.getSupplierName());
        if (supplier == null)
            throw new EntityNotFoundException("Il fornitore non esiste");


        Category category = cRepo.findByName(dto.getCategoryName());
        if (category == null)
            throw new EntityNotFoundException("La categoria non esiste");

        double roundedValue = Math.round(dto.getUnitPrice() * 100.0) / 100.0;

        product.setUnitPrice(roundedValue);
        product.setCategory(category);
        product.setSupplier(supplier);

        product = pRepo.save(product);

        return pServ.toDTO(product);
    }


    @DeleteMapping("{productId}")
    public Integer deleteProduct(@PathVariable Integer productId)
    {
        Optional<Product> productToDelete = pRepo.findById(productId);
        if (productToDelete.isEmpty()) 
            throw new EntityNotFoundException("Il prodotto non esiste");

        Supplier s = productToDelete.get().getSupplier();
        s.getProducts().removeIf(p -> p.getId().equals(productId));

        sRepo.save(s);
    
        return productId;
    }


    @PutMapping("{productId}/updateRemainingUnitsQuantity")
    public void editUnitTypeQuantity(@PathVariable Integer productId, @RequestBody Map<String, Integer> requestBody)
    {
        Integer unitTypeQuantity = requestBody.get("unitTypeQuantity");
        System.out.println(unitTypeQuantity);
        Optional<Product> productToChange = pRepo.findById(productId);
        if (productToChange.isEmpty())
            throw new EntityNotFoundException("il prodotto non esiste");

        Product product = productToChange.get();
        product.setUnitTypeQuantity(unitTypeQuantity);
        pRepo.save(product);
    }

    @PutMapping("{productId}/updateRemainingPackagesQuantity")
    public void editPackagingTypeQuantity(@PathVariable Integer productId, @RequestBody Map<String, Integer> requestBody) 
    {

        Integer packagingTypeQuantity = requestBody.get("packagingTypeQuantity");

        Optional<Product> productToChange = pRepo.findById(productId);
        if (productToChange.isEmpty())
            throw new EntityNotFoundException("il prodotto non esiste");


        Product product = productToChange.get();
        product.setPackagingTypeQuantity(packagingTypeQuantity);
        pRepo.save(product);

    }

    private void validateProductDTO(ProductDTO dto) {
        if (dto.getProductName() == null || dto.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Il nome del prodotto non può essere nullo o vuoto");
        }
        if (dto.getUnitPrice() == null || dto.getUnitPrice() < 0 ) {
            throw new IllegalArgumentException("Il prezzo unitario deve essere maggiore di zero");
        }
        if (dto.getUnitType() == null || dto.getUnitType().isEmpty()) {
            throw new IllegalArgumentException("Il tipo di unità non può essere nullo o vuoto");
        }
        if (dto.getUnitTypeQuantity() == null || dto.getUnitTypeQuantity() < 0) {
            throw new IllegalArgumentException("La quantità dell'unità deve essere almeno 0");
        }
        if (dto.getPackagingType() == null || dto.getPackagingType().isEmpty()) {
            throw new IllegalArgumentException("Il tipo di imballaggio non può essere nullo o vuoto");
        }
        if (dto.getPackagingTypeQuantity() == null || dto.getPackagingTypeQuantity() < 0) {
            throw new IllegalArgumentException("La quantità del tipo di imballaggio deve essere almeno 0");
        }
        if (dto.getUnitsPerPackaging() == null || dto.getUnitsPerPackaging() < 1) {
            throw new IllegalArgumentException("Le unità per imballaggio devono essere almeno 1");
        }
        if (dto.getCategoryName() == null || dto.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Il nome della categoria non può essere nullo o vuoto");
        }
        if (dto.getSupplierName() == null || dto.getSupplierName().isEmpty()) {
            throw new IllegalArgumentException("Il nome del fornitore non può essere nullo o vuoto");
        }
        if (dto.getReorderPoint() == null || dto.getReorderPoint() < 0) {
            throw new IllegalArgumentException("Il punto di riordino non può essere negativo");
        }
        if (dto.getCode() == null || dto.getCode().isEmpty()) {
            throw new IllegalArgumentException("Il codice non può essere nullo o vuoto");
        }

    }

}
