package com.generation.progetto_finale.controller.exceptions;

public class FileTooFatException extends RuntimeException
{
    public FileTooFatException(String message) //Eccezione personalizzata per non permettere di creare più di 6 profili per utente
    {
        super(message);
    }
}
