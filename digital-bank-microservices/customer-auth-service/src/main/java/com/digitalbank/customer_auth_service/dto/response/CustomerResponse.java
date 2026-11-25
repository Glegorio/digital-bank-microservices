package com.digitalbank.customer_auth_service.dto.response;

import com.digitalbank.customer_auth_service.model.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String fullName,
        String cpf,
        String email,
        String phone,
        LocalDate birthDate,
        CustomerStatus status,
        LocalDateTime createdAt
) {}
