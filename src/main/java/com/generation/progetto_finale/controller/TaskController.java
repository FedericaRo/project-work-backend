package com.generation.progetto_finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.TaskDTO;
import com.generation.progetto_finale.dto.mappers.TaskService;
import com.generation.progetto_finale.modelEntity.Task;
import com.generation.progetto_finale.repositories.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/tasks")
public class TaskController 
{
    @Autowired
    TaskRepository tRepo;
    @Autowired
    TaskService tServ;

    @GetMapping
    public List<TaskDTO> getAll() 
    {
        return tServ.toDTO(tRepo.findAll());
    }
    
    @PostMapping ("/newTask")
    public TaskDTO addNewTask(@RequestBody TaskDTO dto) 
    {
        /**
         * Da ReqBody ottengo un DTO e tramite il service lo trasformo in entità
         */
        Task t = tServ.toEntity(dto);

        /**
         * Salvataggio della entità
         */
        t = tRepo.save(t);

        /**
         * Processo inverso rispetto a prima, restituisco l'entità che viene poi salvata come DTO 
         * e infine Jsonizzata
         */
        return tServ.toDTO(t);
    }

}
