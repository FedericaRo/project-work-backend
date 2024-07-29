package com.generation.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {

}
