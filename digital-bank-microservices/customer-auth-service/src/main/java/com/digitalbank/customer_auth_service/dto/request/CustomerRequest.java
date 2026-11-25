package com.digitalbank.customer_auth_service.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CustomerRequest(

        @NotBlank(message = "O nome completo é obrigatório")
        @Size(min = 3, max = 150, message = "O nome deve possuir entre 3 e 150 caracteres")
        String fullName,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve possuir exatamente 11 números")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido")
        @Size(max = 150, message = "o e-mail deve ter no máximo 150 caracteres")
        String email,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
        String phone,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nasscimento deve estar no passado")
        LocalDate birthDate

) {}
