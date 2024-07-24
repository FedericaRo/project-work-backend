package com.generation.progetto_finale.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto extends CredentialsDto
{
    protected String passwordConfirmation;

}
