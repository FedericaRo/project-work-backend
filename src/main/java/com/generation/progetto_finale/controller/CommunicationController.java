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

import com.generation.progetto_finale.dto.CommunicationDTO;
import com.generation.progetto_finale.dto.mappers.CommunicationService;
import com.generation.progetto_finale.modelEntity.Communication;
import com.generation.progetto_finale.repositories.CommunicationRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/communications")
public class CommunicationController 
{
    @Autowired
    CommunicationRepository cRepo;
    @Autowired
    CommunicationService cServ;

    @GetMapping
    public List<CommunicationDTO> getAll()
    {
        return cServ.toDTO(cRepo.findAll());
    }

    @PostMapping("/newCommunication")
    public CommunicationDTO addNewCommunication(@RequestBody CommunicationDTO dto)
    {
        System.out.println(dto);
        /**
         * Da ReqBody ottengo un DTO e tramite il service lo trasformo in entità
         */
        Communication c = cServ.toEntity(dto);

        /**
         * Salvataggio della entità
         */
        c = cRepo.save(c);

        /**
         * Processo inverso rispetto a prima, restituisco l'entità che viene poi salvata come DTO 
         * e infine Jsonizzata
         */
        return cServ.toDTO(c);
    }


    @DeleteMapping("/{id}")
    public Communication deleteCommunication(@PathVariable Integer id)
    {
        Optional<Communication> c = cRepo.findById(id);

        if (c.isEmpty())
            throw new EntityNotFoundException("Comunicazione non esistente");

        cRepo.deleteById(id); 
        
        return c.get();
    }

}
