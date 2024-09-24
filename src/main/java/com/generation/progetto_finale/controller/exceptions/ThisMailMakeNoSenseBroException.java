package com.generation.progetto_finale.controller.exceptions;

public class ThisMailMakeNoSenseBroException extends RuntimeException
{
    public ThisMailMakeNoSenseBroException(String message) //Eccezione personalizzata per la mail degli ordini.
    {
        super(message);
    }
}
