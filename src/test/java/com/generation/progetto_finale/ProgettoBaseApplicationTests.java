package com.generation.progetto_finale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.generation.progetto_finale.auth.model.Role;
import com.generation.progetto_finale.auth.model.UserEntity;
import com.generation.progetto_finale.auth.repository.RoleRepository;
import com.generation.progetto_finale.auth.repository.UserRepository;
import com.generation.progetto_finale.modelEntity.Category;
import com.generation.progetto_finale.modelEntity.Frequency;
import com.generation.progetto_finale.modelEntity.Order;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.modelEntity.Task;
import com.generation.progetto_finale.repositories.CategoryRepository;
import com.generation.progetto_finale.repositories.CommunicationRepository;
import com.generation.progetto_finale.repositories.OrderRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.repositories.SupplierRepository;
import com.generation.progetto_finale.repositories.TaskRepository;
import com.generation.progetto_finale.services.MailService;

import jakarta.mail.MessagingException;

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
    private TaskRepository taskRepo;
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
    void loadRandomOrders() {
    
    // Create and save 10 orders for the selected product
    Random random = new Random();
    for (int i = 1; i <= 10; i++) {
        int productId = random.nextInt(29) + 1; // Generates a random number between 1 and 100
    
        // Fetch the product with the randomly selected ID
        Product product = productRepository.findById(productId).orElse(null);
        
        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
        Order order = new Order();
        order.setProduct(product);

        // Generate random quantities
        int unitOrderedQuantity = random.nextInt(100) + 1; // Random quantity between 1 and 100
        int packagingOrderedQuantity = random.nextInt(20) + 1; // Random quantity between 1 and 20

        order.setUnitOrderedQuantity(unitOrderedQuantity);
        order.setPackagingOrderedQuantity(packagingOrderedQuantity);
        order.setOrderDate(LocalDate.now());
        // order.setDeliverDate(LocalDate.now().plusDays(i));
        orderRepository.save(order);
    }
}

@Test
void addUniqueProductsWithUniqueSuppliers() {
    Random random = new Random();
    List<Supplier> suppliers = new ArrayList<>();
    List<Category> categories = new ArrayList<>();

    // Creare 5 categorie uniche
    for (int i = 1; i <= 5; i++) {
        Category category = new Category();
        category.setName("Category " + i);
        categoryRepository.save(category);
        categories.add(category);
    }

    // Creare 30 fornitori unici
    for (int i = 1; i <= 30; i++) {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier " + i);
        supplierRepository.save(supplier);
        suppliers.add(supplier);
    }

    // Creare 30 prodotti unici con codici e fornitori distinti
    for (int i = 1; i <= 30; i++) {
        Product product = new Product();
        product.setProductName("Product " + i);
        product.setCode("PROD" + String.format("%03d", i)); // Codice univoco per ogni prodotto
        product.setUnitPrice(5.0 + (random.nextDouble() * 95.0)); // Prezzo tra 5.0 e 100.0
        product.setUnitType(randomUnitType());
        product.setUnitTypeQuantity(50 + random.nextInt(451)); // Quantità tra 50 e 500
        product.setPackagingType(randomPackagingType());
        product.setPackagingTypeQuantity(5 + random.nextInt(96)); // Quantità tra 5 e 100
        product.setUnitsPerPackaging(1 + random.nextInt(20)); // Unità per confezione tra 1 e 20
        product.setReorderPoint(2 + random.nextInt(10));
        product.setSupplier(suppliers.get(i - 1)); // Fornitore distinto per ogni prodotto
        product.setCategory(categories.get(random.nextInt(categories.size()))); // Seleziona una delle 5 categorie
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





    // @Test
    // public void provaAutomazioneTask()
    // {
    //     List<StoredTask> tasks = stRepo.findAllByFrequency(Frequency.SETTIMANALE);
    //     List<Task> realTasks = new ArrayList<>();

    //     for (StoredTask st : tasks) 
    //     {
    //         Task task = new Task();

    //         task.setName(st.getName());
    //         task.setDescription(st.getDescription());
    //         task.setFrequency(st.getFrequency());
    //         task.setStatus(TaskStatus.DAFARSI);

    //         realTasks.add(task);
    //     }

    //     taskRepo.saveAll(realTasks);
    // }


    @Test
    public void addTasks()
    {
        List<Task> tasks = new ArrayList<>();
        
        tasks.add(createTask(
                "Verifica Sistema Settimanale",
                "Eseguire una verifica completa del sistema ogni settimana per garantire che tutto funzioni correttamente.",
                Frequency.SETTIMANALE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Aggiornamento Documentazione Mensile",
                "Aggiornare la documentazione aziendale e i manuali con le ultime informazioni disponibili ogni mese.",
                Frequency.MENSILE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Controllo Backup Bisettimanale",
                "Controllare e assicurarsi che i backup siano stati effettuati correttamente ogni due settimane.",
                Frequency.BISETTIMANALE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Pulizia Server Settimanale",
                "Eseguire una pulizia dei server per rimuovere file temporanei e ottimizzare le performance settimanalmente.",
                Frequency.SETTIMANALE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Rivedere Politiche di Sicurezza Mensile",
                "Rivedere e aggiornare le politiche di sicurezza aziendale per garantire che siano sempre aggiornate ogni mese.",
                Frequency.MENSILE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Verifica Licenze Software Bisettimanale",
                "Verificare la validità e lo stato delle licenze software ogni due settimane per evitare problemi di conformità.",
                Frequency.BISETTIMANALE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Preparazione Report Settimanale",
                "Preparare il report settimanale con le metriche e i risultati delle attività.",
                Frequency.SETTIMANALE,
                Task.TaskStatus.DAFARSI
        ));
        
        tasks.add(createTask(
                "Aggiornamento Elenco Contatti Mensile",
                "Aggiornare l'elenco dei contatti aziendali per assicurarsi che tutte le informazioni siano corrette ogni mese.",
                Frequency.MENSILE,
                Task.TaskStatus.DAFARSI
        ));
        taskRepo.saveAll(tasks);
        
        // Printing tasks to verify creation
        // tasks.forEach(task -> {
        //     System.out.println("Name: " + task.getName());
        //     System.out.println("Description: " + task.getDescription());
        //     System.out.println("Frequency: " + task.getFrequency());
        //     System.out.println("Status: " + task.getStatus());
        //     System.out.println("Creation Date: " + task.getCreationDate());
        //     System.out.println("Completion Date: " + task.getCompletionDate());
        //     System.out.println("----------------------------");
        // });
    }


    private static Task createTask(String name, String description, Frequency frequency, Task.TaskStatus status) 
    {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setFrequency(frequency);
        task.setStatus(status);
        task.setCreationDate(LocalDate.now());
        return task;
    }

    @Test
    public void addOldTasks() {
        List<Task> oldTasks = new ArrayList<>();
        
        oldTasks.add(createOldTask(
                "Verifica Sistema Settimanale",
                "Eseguire una verifica completa del sistema ogni settimana per garantire che tutto funzioni correttamente.",
                Frequency.SETTIMANALE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusWeeks(2) // Data di creazione di 2 settimane fa
        ));
        
        oldTasks.add(createOldTask(
                "Aggiornamento Documentazione Mensile",
                "Aggiornare la documentazione aziendale e i manuali con le ultime informazioni disponibili ogni mese.",
                Frequency.MENSILE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusMonths(1) // Data di creazione di 1 mese fa
        ));
        
        oldTasks.add(createOldTask(
                "Controllo Backup Bisettimanale",
                "Controllare e assicurarsi che i backup siano stati effettuati correttamente ogni due settimane.",
                Frequency.BISETTIMANALE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusWeeks(3) // Data di creazione di 3 settimane fa
        ));
        
        oldTasks.add(createOldTask(
                "Pulizia Server Settimanale",
                "Eseguire una pulizia dei server per rimuovere file temporanei e ottimizzare le performance settimanalmente.",
                Frequency.SETTIMANALE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusWeeks(4) // Data di creazione di 4 settimane fa
        ));
        
        oldTasks.add(createOldTask(
                "Rivedere Politiche di Sicurezza Mensile",
                "Rivedere e aggiornare le politiche di sicurezza aziendale per garantire che siano sempre aggiornate ogni mese.",
                Frequency.MENSILE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusMonths(2) // Data di creazione di 2 mesi fa
        ));
        
        oldTasks.add(createOldTask(
                "Verifica Licenze Software Bisettimanale",
                "Verificare la validità e lo stato delle licenze software ogni due settimane per evitare problemi di conformità.",
                Frequency.BISETTIMANALE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusWeeks(5) // Data di creazione di 5 settimane fa
        ));
        
        oldTasks.add(createOldTask(
                "Preparazione Report Settimanale",
                "Preparare il report settimanale con le metriche e i risultati delle attività.",
                Frequency.SETTIMANALE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusWeeks(6) // Data di creazione di 6 settimane fa
        ));
        
        oldTasks.add(createOldTask(
                "Aggiornamento Elenco Contatti Mensile",
                "Aggiornare l'elenco dei contatti aziendali per assicurarsi che tutte le informazioni siano corrette ogni mese.",
                Frequency.MENSILE,
                Task.TaskStatus.DAFARSI,
                LocalDate.now().minusMonths(3) // Data di creazione di 3 mesi fa
        ));

        taskRepo.saveAll(oldTasks);
    }

    private static Task createOldTask(String name, String description, Frequency frequency, Task.TaskStatus status, LocalDate creationDate) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setFrequency(frequency);
        task.setStatus(status);
        task.setCreationDate(creationDate);
        return task;
    }

    @Autowired
    MailService mailService;

    @Test
    public void mandaMail() throws MessagingException
    {
        Map<String,Object> model = new HashMap<>();
        model.put("campo1", "ciaoo");
        model.put("campo2", "byee");

        mailService.sendHtmlMessage("rocchetti.federica@gmail.com", "mail prova", model);
    }

    @Test
    public void printOrdersBetweenDates()
    {
        // LocalDate now = LocalDate.now();
        // LocalDate before = LocalDate.now().minusDays(1);
        // List<Order> orders = orderRepository.findAllByOrderDateBetween(before, now);
        
        // for (Order o : orders) 
        // {
        //     System.out.println(o);
        // }

        LocalDate now = LocalDate.now();
        LocalDate before = LocalDate.now().minusDays(1);

        // ID attesi (sostituisci con i tuoi dati di test)
        List<Integer> expectedIds = Arrays.asList(5, 11);

        List<Order> orders = orderRepository.findAllByOrderDateBetween(before, now);

        // Estrai gli ID dagli ordini
        List<Integer> actualIds = orders.stream()
                                        .map(Order::getId)
                                        .collect(Collectors.toList());

        // Confronta gli ID attesi con quelli effettivi
        assertEquals(expectedIds, actualIds);
    }

}



