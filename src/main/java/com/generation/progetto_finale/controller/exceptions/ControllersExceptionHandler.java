package com.generation.progetto_finale.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllersExceptionHandler 
{
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String entityNotFound(EntityNotFoundException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public String illegalArgument(IllegalArgumentException e)
    {
        return e.getMessage();
    }

    // @ExceptionHandler(HttpMessageNotReadableException.class)
    // @ResponseStatus(code = HttpStatus.FORBIDDEN)
    // public String httpMessageNotReadable(HttpMessageNotReadableException e)
    // {
    //     // return e.getMessage();
    //     return "Input non valido";
    // }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public String numberFormat(NumberFormatException e)
    {
        // return e.getMessage();
        return "Input non valido";
    }

    @ExceptionHandler(ThisMailMakeNoSenseBroException.class)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public String noOrdersToSendFound(ThisMailMakeNoSenseBroException e)
    {
        return "Nessun ordine da inviare, assicurati di aver prima completato la lista dei tuoi ordini";
    }


    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String mailNotSended(MessagingException e)
    {
        return "Non è stato possibile inviare la mail a causa di problemi di connessione con il sever";
    }


}
