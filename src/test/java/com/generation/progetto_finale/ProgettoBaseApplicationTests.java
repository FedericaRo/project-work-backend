package com.generation.progetto_finale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.generation.progetto_finale.auth.model.Role;
import com.generation.progetto_finale.auth.model.UserEntity;
import com.generation.progetto_finale.auth.repository.RoleRepository;
import com.generation.progetto_finale.auth.repository.UserRepository;
import com.generation.progetto_finale.modelEntity.Category;

import com.generation.progetto_finale.modelEntity.Order;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.repositories.CategoryRepository;
import com.generation.progetto_finale.repositories.OrderRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.repositories.SupplierRepository;

import com.generation.progetto_finale.modelEntity.Communication;
import com.generation.progetto_finale.modelEntity.Communication.CommunicationImportance;
import com.generation.progetto_finale.modelEntity.Communication.CommunicationType;
import com.generation.progetto_finale.modelEntity.Frequency;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.modelEntity.StoredTask;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.modelEntity.Task;
import com.generation.progetto_finale.modelEntity.Task.TaskStatus;

import com.generation.progetto_finale.repositories.CategoryRepository;
import com.generation.progetto_finale.repositories.CommunicationRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.repositories.StoredTaskRepository;
import com.generation.progetto_finale.repositories.SupplierRepository;
import com.generation.progetto_finale.repositories.TaskRepository;

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
	private OrderRepository orderRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired

    private CommunicationRepository communicationRepository;


    @Test
    void addNewCommunication()
    {
        Communication communication1;
        Communication communication2;
        Communication communication3;
        Communication communication4;
        Communication communication5;
        Communication communication6;
        Communication communication7;
        Communication communication8;
        Communication communication9;

        // Prima comunicazione
        communication1 = new Communication();
        communication1.setFromPerson("Paola");
        communication1.setToPerson("Massimo");
        communication1.setType(CommunicationType.AMMINISTRATIVA);
        communication1.setDescription("Circolare nuovi fornitori");
        communication1.setCreationDate(LocalDate.of(2024, 7, 29));
        communication1.setImportance(CommunicationImportance.ALTA);
        communication1.setCommunicationName("Nuovi Fornitori");
        communicationRepository.save(communication1);

        // Seconda comunicazione
        communication2 = new Communication();
        communication2.setFromPerson("Cristiano Malgioglio");
        communication2.setToPerson("Morgan");
        communication2.setType(CommunicationType.ORGANIZZATIVA);
        communication2.setDescription("Meeting oggi alle 16:00");
        communication2.setCreationDate(LocalDate.of(2024, 7, 28));
        communication2.setImportance(CommunicationImportance.MEDIA);
        communication2.setCommunicationName("Meeting ore 16:00");
        communicationRepository.save(communication2);

        // Terza comunicazione
        communication3 = new Communication();
        communication3.setFromPerson("Eva");
        communication3.setToPerson("Tutti");
        communication3.setType(CommunicationType.INFORMATIVA);
        communication3.setDescription("Festa di compleanno di Mario");
        communication3.setCreationDate(LocalDate.of(2024, 7, 27));
        communication3.setImportance(CommunicationImportance.BASSA);
        communication3.setCommunicationName("Compleanno Mario");
        communicationRepository.save(communication3);

        // Quarta comunicazione
        communication4 = new Communication();
        communication4.setFromPerson("Gianluca");
        communication4.setToPerson("Santo");
        communication4.setType(CommunicationType.CAMBIOTURNO);
        communication4.setDescription("AO dovemo cambià turno, io me butto sul divano");
        communication4.setCreationDate(LocalDate.of(2024, 7, 28));
        communication4.setImportance(CommunicationImportance.ALTA);
        communication4.setCommunicationName("Cambio Turno");
        communicationRepository.save(communication4);

        // Quinta comunicazione
        communication5 = new Communication();
        communication5.setFromPerson("Giovanni");
        communication5.setToPerson("Anna");
        communication5.setType(CommunicationType.AMMINISTRATIVA);
        communication5.setDescription("Modifica regolamenti aziendali");
        communication5.setCreationDate(LocalDate.of(2024, 7, 26));
        communication5.setImportance(CommunicationImportance.MEDIA);
        communication5.setCommunicationName("Regolamenti Aziendali");
        communicationRepository.save(communication5);

        // Sesta comunicazione
        communication6 = new Communication();
        communication6.setFromPerson("Laura");
        communication6.setToPerson("Stefano");
        communication6.setType(CommunicationType.INFORMATIVA);
        communication6.setDescription("Modifica credenziali portale amministrativo");
        communication6.setCreationDate(LocalDate.of(2024, 7, 30));
        communication6.setImportance(CommunicationImportance.BASSA);
        communication6.setCommunicationName("Credenziali portale amministrativo");
        communicationRepository.save(communication6);

        // Settima comunicazione
        communication7 = new Communication();
        communication7.setFromPerson("Marco");
        communication7.setToPerson("Elena");
        communication7.setType(CommunicationType.ORGANIZZATIVA); // Puoi modificare il tipo se necessario
        communication7.setDescription("Incontro urgente con il team di sviluppo");
        communication7.setCreationDate(LocalDate.of(2024, 8, 1));
        communication7.setImportance(CommunicationImportance.ALTA);
        communication7.setCommunicationName("Incontro Team Sviluppo");
        communicationRepository.save(communication7);

        // Ottava comunicazione
        communication8 = new Communication();
        communication8.setFromPerson("Maria");
        communication8.setToPerson("Luca");
        communication8.setType(CommunicationType.AMMINISTRATIVA);
        communication8.setDescription("Aggiornamento policy aziendale");
        communication8.setCreationDate(LocalDate.of(2024, 7, 31));
        communication8.setImportance(CommunicationImportance.MEDIA);
        communication8.setCommunicationName("Aggiornamento Policy");
        communicationRepository.save(communication8);

        // Nona comunicazione
        communication9 = new Communication();
        communication9.setFromPerson("Giuseppe");
        communication9.setToPerson("Silvia");
        communication9.setType(CommunicationType.INFORMATIVA);
        communication9.setDescription("Lancio nuovo prodotto");
        communication9.setCreationDate(LocalDate.of(2024, 8, 2));
        communication9.setImportance(CommunicationImportance.ALTA);
        communication9.setCommunicationName("Lancio Nuovo Prodotto");
        communicationRepository.save(communication9);
    }


    StoredTaskRepository stRepo;
    @Autowired
    TaskRepository tRepo;
    
   
   

	
	@Test
	void addUser() 
	{
		UserEntity admin = new UserEntity();
	
		Role roles = roleRepository.findByName("ADMIN").get();
		List<Role> rl = new ArrayList<>();
		rl.add(roles);
        admin.setRoles(rl);
		admin.setUsername("fra@email.com");


		admin.setPassword(passwordEncoder.encode(("1234")));


        userRepository.save(admin);

		
		// francesca.setRoles();
	}

	@Test

    void addProduct()
    {
        Supplier supplierA = new Supplier();
        supplierA.setName("Supplier A");
        supplierA.setCode("SUP123");
        supplierRepository.save(supplierA);



		// Creare il primo prodotto
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



	@Test
	void loadOrders()
	{
		Supplier supplier = supplierRepository.findById(1).orElse(null);
        Category category = categoryRepository.findById(1).orElse(null);

        // Crea un prodotto
        Product product = new Product();
        product.setProductName("Product A");
        product.setUnitPrice(10.0);
        product.setUnitType("PZ");
        product.setUnitTypeQuantity(100);
        product.setPackagingType("CT");
        product.setPackagingTypeQuantity(10);
        product.setUnitsPerPackaging(10);
        product.setCategory(category);
        product.setSupplier(supplier);
        productRepository.save(product);

        // Crea e salva 10 ordini
        for (int i = 1; i <= 10; i++) {
            Order order = new Order();
            order.setProduct(product);
            order.setUnitOrderedQuantity(50 + i);
            order.setUnitDeliveredQuantity(45 + i);
            order.setPackagingOrderedQuantity(5 + i);
            order.setPackagingDeliveredQuantity(4 + i);
            order.setOrderDate(LocalDate.now());
            order.setDeliverDate(LocalDate.now().plusDays(i));
            orderRepository.save(order);
        }
	}



    @Test
    public void provaAutomazioneTask()
    {
        List<StoredTask> tasks = stRepo.findAllByFrequency(Frequency.SETTIMANALE);
        List<Task> realTasks = new ArrayList<>();

        for (StoredTask st : tasks) 
        {
            Task task = new Task();

            task.setName(st.getName());
            task.setDescription(st.getDescription());
            task.setFrequency(st.getFrequency());
            task.setStatus(TaskStatus.DAFARSI);

            realTasks.add(task);
        }

        tRepo.saveAll(realTasks);
    }

}


