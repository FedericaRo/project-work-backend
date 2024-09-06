package com.generation.progetto_finale.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Communication;

public interface CommunicationRepository extends JpaRepository<Communication,Integer>
{
    List<Communication> findByCreationDateBefore(LocalDateTime dateTime);
}
