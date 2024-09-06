package com.generation.progetto_finale.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public String illegalArgument(IllegalArgumentException e)
    {
        return e.getMessage();
    }


    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String notInRange(IndexOutOfBoundsException e)
    {
        return e.getMessage();
    }


    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public String numberFormat(NumberFormatException e)
    {
        // return e.getMessage();
        return "Input non valido";
    }

    @ExceptionHandler(DoYouWantAFootballTeamException.class)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public String maxProfileNumberReached(DoYouWantAFootballTeamException e)
    {
        return "Hai già raggiunto il numero massimo di profili per questo utente.";
    }

    @ExceptionHandler(ThisMailMakeNoSenseBroException.class)
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public String noOrdersToSendFound(ThisMailMakeNoSenseBroException e)
    {
        return "Nessun ordine da inviare, assicurati di aver prima completato la lista dei tuoi ordini";
    }


    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String mailNotSent(MessagingException e)
    {
        return "Non è stato possibile inviare la mail a causa di problemi di connessione con il sever";
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String badCredentials(BadCredentialsException e)
    {
        System.out.println("exception authentication");
        return "Username o password non valido";
    }


}
