package com.generation.progetto_finale.controller.exceptions;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String message) //Eccezione personalizzata per tutti i metodi che dovrebbero trovare qualcosa ma non lo trovano.
    {
        super(message);
    }
}

