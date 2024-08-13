package com.generation.progetto_finale.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.controller.exceptions.ThisMailMakeNoSenseBroException;
import com.generation.progetto_finale.dto.OrderDTO;
import com.generation.progetto_finale.dto.mappers.OrderService;
import com.generation.progetto_finale.modelEntity.Order;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.repositories.OrderRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.services.MailService;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * REST controller for managing orders.
 * Provides endpoints to perform CRUD operations on orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController 
{
    

    @Autowired
    ProductRepository productRepo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired 
    OrderService orderServ;

    @Autowired
    MailService mailService;


    /**
     * Retrieves all orders from the repository and converts them to DTOs.
     *
     * @return a list of {@link OrderDTO} objects representing all orders.
     */
    @GetMapping
    public List<OrderDTO> getAll()
    {
        return orderServ.toDTO(orderRepo.findAll());
    }


     @PostMapping("{productId}/addOrder")
     public OrderDTO addNewOrder(@PathVariable Integer productId, @RequestBody Map<String, String> requestBody) 
     {         
    
        
        // Integer packagingOrderedQuantity = requestBody.get("packagingOrderedQuantity");
        // System.out.println(packagingOrderedQuantity);

        // Integer unitOrderedQuantity = requestBody.get("unitOrderedQuantity");
        // System.out.println(unitOrderedQuantity);


         // Get values from the request body with default to 0 if null
        // Integer packagingOrderedQuantity = Optional.ofNullable(requestBody.get("packagingOrderedQuantity")).orElse(0);
        // Integer unitOrderedQuantity = Optional.ofNullable(requestBody.get("unitOrderedQuantity")).orElse(0);
        Integer unitOrderedQuantity;
        if (requestBody.get("unitOrderedQuantity") == null)
            unitOrderedQuantity = 0;
        else
            unitOrderedQuantity = Integer.parseInt(requestBody.get("unitOrderedQuantity"));

        Integer packagingOrderedQuantity;
        if (requestBody.get("packagingOrderedQuantity") == null)
        packagingOrderedQuantity = 0;
        else
        packagingOrderedQuantity = Integer.parseInt(requestBody.get("packagingOrderedQuantity"));

        // Validate that neither quantity is negative
        if (packagingOrderedQuantity < 0 || unitOrderedQuantity < 0) {
            throw new IllegalArgumentException("Le quantità dell'ordine non possono essere negative");
        }

        // Validate that at least one quantity is greater than 0
        if (packagingOrderedQuantity == 0 && unitOrderedQuantity == 0) {
            throw new IllegalArgumentException("Aggiungere almeno una quantità all'ordine");
        }

        Optional<Product> productOptional = productRepo.findById(productId);
        if (productOptional.isEmpty())
            throw new EntityNotFoundException("Il prodotto non esiste");

        Product product = productOptional.get();
        Order newOrder = new Order();

        newOrder.setProduct(product);
        newOrder.setUnitOrderedQuantity(unitOrderedQuantity);
        newOrder.setPackagingOrderedQuantity(packagingOrderedQuantity);

        orderRepo.save(newOrder);

        System.out.println("----------");
        System.out.println(newOrder.getId());
        System.out.println(newOrder.getUnitOrderedQuantity());
        System.out.println(newOrder.getPackagingOrderedQuantity());
        System.out.println(newOrder.getOrderDate());
        
        return orderServ.toDTO(newOrder); 
     }
     



    /**
     * Updates the details of an existing order when the order arrived status changes. 
     * Updates the arrival status of the order and updates the associated product quantities based on the new status.
     *
     * @param orderId the ID of the order to update. Must be a positive integer.
     * @param orderDTO object containing the updated order details, including the arrival status.
     * @throws IllegalArgumentException if the order ID is null or not a positive integer, or if the order DTO is null.
     * @throws EntityNotFoundException if the order with the specified ID does not exist.
     */
    @PutMapping("{orderId}/updateOrderArrivalDetails")
    public OrderDTO updateOrderArrivalDetails(@PathVariable Integer orderId, @RequestBody OrderDTO orderDTO) 
    {
        
        if (orderDTO == null) 
            throw new IllegalArgumentException("Order details were not provided");
        

        if (orderId == null || orderId <= 0) 
            throw new IllegalArgumentException("Order id not valid");
        

        System.out.println("STATUS ARRIVATO DA CAMBIARE " + orderDTO.isArrived());
        Optional<Order> orderToChange = orderRepo.findById(orderId);
        if (orderToChange.isEmpty())
            throw new EntityNotFoundException("L'ordine non esiste");
            
        Order order = orderToChange.get();
            
        // Update the arrival status of the order and obtain the updated order
        Order updatedOrder = updateOrderArrivalStatus(order);


        Product product = order.getProduct();

        // Update the product quantities based on the new order arrival status and obtain the updated product
        Product updatedProduct = updateProductQuantities(product, order);


        System.out.println("OLD PRODUCT UNIT QUANTITY " +product.getUnitTypeQuantity()); 
        System.out.println("OLD PRODUCT PACKAGING QUANTITY " +product.getPackagingTypeQuantity()); 

        productRepo.save(updatedProduct);
        orderRepo.save(updatedOrder);

        return orderServ.toDTO(updatedOrder);



       
    }

    /**
     * Updates the arrival status of the given order. The new status is the inverse of the current status.
     *
     * @param order the order to update.
     * @return the updated order with the new arrival status.
     */
    private Order updateOrderArrivalStatus(Order order) 
    {
        boolean newArrivalStatus = !order.isArrived();
        order.setArrived(newArrivalStatus);
        if (newArrivalStatus)
            order.setDeliverDate(LocalDate.now());
        else
            order.setDeliverDate(null);

        System.out.println("CURRENT ORDER STATUS: " + !newArrivalStatus);
        System.out.println("NEW ORDER STATUS: " + newArrivalStatus);

        return order;
    }

    /**
     * Updates the quantities of the given product based on the order's arrival status.
     * If the order has arrived, the ordered quantities are added to the product's quantities.
     * If the order gets updated to not arrived, the ordered quantities are subtracted from the product's quantities.
     *
     * @param product the product whose quantities will be updated.
     * @param order the order that determines the quantity changes.
     * @return the updated product with the new quantities.
     */
    private Product updateProductQuantities(Product product, Order order) 
    {
        int packagingChange = order.isArrived() ? order.getPackagingOrderedQuantity() : -order.getPackagingOrderedQuantity();
        int unitChange = order.isArrived() ? order.getUnitOrderedQuantity() : -order.getUnitOrderedQuantity();
    
        if (packagingChange != 0) {
            product.setPackagingTypeQuantity(product.getPackagingTypeQuantity() + packagingChange);
            System.out.println("NEW PRODUCT PACKAGING QUANTITY: " + product.getPackagingTypeQuantity());
        }
    
        if (unitChange != 0) {
            product.setUnitTypeQuantity(product.getUnitTypeQuantity() + unitChange);
            System.out.println("NEW PRODUCT UNIT QUANTITY: " + product.getUnitTypeQuantity());
        }

        return product;
    }

    // TO DO Aggiungere controllo se non arriva un id valido come intero o un intero nella mappa
    @PutMapping("{orderId}/editPackagingQuantity")
    public void editPackagingQuantity(@PathVariable Integer orderId, @RequestBody Map<String, Integer> requestBody)
    {
        Integer packagingOrderedQuantity = requestBody.get("packagingOrderedQuantity");
        System.out.println(packagingOrderedQuantity);
        Optional<Order> orderToChange = orderRepo.findById(orderId);
        if (orderToChange.isEmpty())
            throw new EntityNotFoundException("L'ordine non esiste");
    
        Order order = orderToChange.get();
        System.out.println(order.getProduct().getProductName());
        order.setPackagingOrderedQuantity(packagingOrderedQuantity);
        orderRepo.save(order);
    }

    // TO DO Aggiungere controllo se non arriva un id valido come intero o un intero nella mappa
    @PutMapping("{orderId}/editUnitQuantity")
    public void editUnitQuantity(@PathVariable Integer orderId, @RequestBody Map<String, Integer> requestBody) 
    {

        // try {
            Integer unitOrderedQuantity = requestBody.get("unitOrderedQuantity");
        // } catch (Exception e) {
        //     throw new DataNotVa
        // }
        
        Optional<Order> orderToChange = orderRepo.findById(orderId);
        if (orderToChange.isEmpty()) 
            throw new EntityNotFoundException("L'ordine non esiste");

        Order order = orderToChange.get();
        System.out.println(order.getProduct().getProductName());
        order.setUnitOrderedQuantity(unitOrderedQuantity);
        orderRepo.save(order);

    }

    // TO DO Aggiungere controllo se non arriva un id valido come intero
    @DeleteMapping("{orderId}")
    public OrderDTO deleteOrder(@PathVariable Integer orderId)
    {
        Optional<Order> orderToDelete = orderRepo.findById(orderId);
        if (orderToDelete.isEmpty()) 
            throw new EntityNotFoundException("L'ordine non esiste");

        orderRepo.delete(orderToDelete.get());

        return orderServ.toDTO(orderToDelete.get());
    }

   
    // public String getMethodName(@RequestParam String param) {
    //     return new String();
    // }
    
    // TODO Aggiungere controllo se non arriva un id valido come intero
    @DeleteMapping("/deleteLast/{productName}")
    public OrderDTO deleteLastOrder(@PathVariable String productName)
    {
        Product product = productRepo.findByProductName(productName);
        
        List<Order> orders = orderRepo.findAllByProductId(product.getId());

        Order orderToDelete = orders.get(orders.size()-1);

        if (orderToDelete == null) 
            throw new EntityNotFoundException("L'ordine non esiste");

        orderRepo.delete(orderToDelete);

        return orderServ.toDTO(orderToDelete);
    }

   
    
    @GetMapping("/sendOrders")
    public List<OrderDTO> sendMail() throws MessagingException
    {
        LocalDate now = LocalDate.now();
        LocalDate before = LocalDate.now().minusDays(1);
        Map<String,Object> model = new HashMap<>();
        List<OrderDTO> ordersDTO = orderServ.toDTO(orderRepo.findAllByOrderDateBetween(before, now)).stream().filter(o -> o.isArrived() == false).toList();

        if (ordersDTO.size() == 0)
            throw new ThisMailMakeNoSenseBroException("Non c'è alcun ordine da inviare");

        // model.put("campo1", "ciaoo");
        // model.put("campo2", "byee");
        model.put("orders", ordersDTO);

        mailService.sendHtmlMessage("santocaldarella@gmail.com", "mail prova", model);
        // rocchetti.federica@gmail.com
        return ordersDTO;
    }






}
