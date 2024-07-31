package com.generation.progetto_finale.controller.exceptions;

public class NotNewEntityException extends RuntimeException
{
    public NotNewEntityException(String message) //Eccezione personalizzata per tutti i metodi che dovrebbero inserire una entità ma già esiste nel DB.
    {
        super(message);
    }
}
