package com.generation.progetto_finale.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.core.JsonParseException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;

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


    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String notInRange(IndexOutOfBoundsException e)
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

    @ExceptionHandler(DoYouWantAFootballTeamException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public String maxProfileNumberReached(DoYouWantAFootballTeamException e)
    {
        // return e.getMessage();
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


    @ExceptionHandler(FileTooFatException.class)
    @ResponseStatus(code = HttpStatus.PAYLOAD_TOO_LARGE)
    public String fileTooLarge(FileTooFatException e)
    {
        return "File troppo grande, limite consentito: 5MB.";
    }


    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(code = HttpStatus.FORBIDDEN)
    // public String authenticationFailed(Exception e)
    // {
    //     return "Sessione scaduta, rifai il login.";
    // }
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String badCredentials(BadCredentialsException e)
    {
        System.out.println("exception authentication");
        return "Username o password non valido";
    }

    // @ExceptionHandler(MaxUploadSizeExceededException.class)
    // @ResponseStatus(code = HttpStatus.PAYLOAD_TOO_LARGE)
    // public String maxFile(MaxUploadSizeExceededException e)
    // {
    //     System.out.println("file max 5mb");
    //     return "Massimo file 5mb";
    // }



}
