package com.generation.progetto_finale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.progetto_finale.auth.model.Role;
import com.generation.progetto_finale.auth.model.UserEntity;
import com.generation.progetto_finale.auth.repository.RoleRepository;
import com.generation.progetto_finale.auth.repository.UserRepository;
import com.generation.progetto_finale.modelEntity.Category;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.repositories.CategoryRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.repositories.SupplierRepository;

@SpringBootTest
class ProgettoBaseApplicationTests 
{
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    
   
   
	
	@Test
	void addUser() 
	{
		UserEntity dipendente = new UserEntity();
	
		Role roles = roleRepository.findByName("DIPENDENTE").get();
		List<Role> rl = new ArrayList<>();
		rl.add(roles);
        dipendente.setRoles(rl);
		dipendente.setUsername("dipendente@email.com");


		dipendente.setPassword(passwordEncoder.encode(("1234")));


        userRepository.save(dipendente);

		
		// francesca.setRoles();
	}

	@Test
    void addProduct()
    {
        Supplier supplierA = new Supplier();
        supplierA.setName("Supplier A");
        supplierA.setCode("SUP123");
        supplierRepository.save(supplierA);

        Category categoryA = new Category();
        categoryA.setName("Category A");
        categoryRepository.save(categoryA);

        // Creare il primo prodotto
        Product productA = new Product();
        productA.setProductName("Product A");
        productA.setUnitPrice(10.0);
        productA.setUnitType("PZ");
        productA.setUnitTypeQuantity(100);
        productA.setPackagingType("CT");
        productA.setPackagingTypeQuantity(10);
        productA.setUnitsPerPackaging(10);
        productA.setSupplier(supplierA);
        productA.setCategory(categoryA);
        productRepository.save(productA);

        // Creare il secondo supplier
        Supplier supplierB = new Supplier();
        supplierB.setName("Supplier B");
        supplierB.setCode("SUP456");
        supplierRepository.save(supplierB);

        // Creare la seconda categoria
        Category categoryB = new Category();
        categoryB.setName("Category B");
        categoryRepository.save(categoryB);

        // Creare il secondo prodotto
        Product productB = new Product();
        productB.setProductName("Product B");
        productB.setUnitPrice(20.0);
        productB.setUnitType("KG");
        productB.setUnitTypeQuantity(200);
        productB.setPackagingType("CON");
        productB.setPackagingTypeQuantity(20);
        productB.setUnitsPerPackaging(5);
        productB.setSupplier(supplierB);
        productB.setCategory(categoryB);
        productRepository.save(productB);
    }

    @Test
    void addMoreProducts()
    {
        Random random = new Random();
        List<Supplier> suppliers = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        // Creare fornitori e categorie iniziali
        for (int i = 1; i <= 5; i++) {
            Supplier supplier = new Supplier();
            supplier.setName("Supplier " + i);
            supplier.setCode("SUP" + String.format("%03d", i * 100));
            supplierRepository.save(supplier);
            suppliers.add(supplier);

            Category category = new Category();
            category.setName("Category " + i);
            categoryRepository.save(category);
            categories.add(category);
        }

        // Creare 30 prodotti casuali
        for (int i = 1; i <= 98; i++) {
            Product product = new Product();
            product.setProductName("Product " + i);
            product.setUnitPrice(5.0 + (random.nextDouble() * 95.0)); // Prezzo tra 5.0 e 100.0
            product.setUnitType(randomUnitType());
            product.setUnitTypeQuantity(50 + random.nextInt(451)); // Quantità tra 50 e 500
            product.setPackagingType(randomPackagingType());
            product.setPackagingTypeQuantity(5 + random.nextInt(96)); // Quantità tra 5 e 100
            product.setUnitsPerPackaging(1 + random.nextInt(20)); // Unità per confezione tra 1 e 20
            product.setSupplier(suppliers.get(random.nextInt(suppliers.size())));
            product.setCategory(categories.get(random.nextInt(categories.size())));
            productRepository.save(product);
        }
    }

    private String randomUnitType() {
        String[] unitTypes = {"PZ", "KG", "L", "M", "CM"};
        return unitTypes[new Random().nextInt(unitTypes.length)];
    }

    private String randomPackagingType() {
        String[] packagingTypes = {"CT", "CON", "BOX", "BAG", "PAL"};
        return packagingTypes[new Random().nextInt(packagingTypes.length)];
    }
}
