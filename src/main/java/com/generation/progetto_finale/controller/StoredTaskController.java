package com.generation.progetto_finale.controller;

import java.util.List;
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

import com.generation.progetto_finale.modelEntity.StoredTask;
import com.generation.progetto_finale.repositories.StoredTaskRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/storedTasks")
public class StoredTaskController 
{
    @Autowired
    StoredTaskRepository stRepo;

    @GetMapping
    public List<StoredTask> getAll() 
    {
        return stRepo.findAll();
    }


    @PostMapping("/newStoredTask")
    public StoredTask newStoredTask(@RequestBody StoredTask stt) 
    {
        return stt = stRepo.save(stt);
    }


    @PutMapping("/{id}")
    public StoredTask updateTask(@PathVariable Integer id, @RequestBody StoredTask stt) 
    {
        StoredTask sTask = stRepo.findById(id).get();
        
        sTask.setName(stt.getName());
        sTask.setDescription(stt.getDescription());
        sTask.setFrequency(stt.getFrequency());
        
        stt = stRepo.save(sTask);

        return stt;
    }


    @DeleteMapping("/{id}") 
    public StoredTask delete(@PathVariable int id) 
    {
        Optional<StoredTask> opt = stRepo.findById(id); 

        if (opt.isEmpty()) 
            throw new EntityNotFoundException("Task di Backup con id " +id+ " non trovata");

            stRepo.deleteById(id);

        return opt.get(); 
    }

}
