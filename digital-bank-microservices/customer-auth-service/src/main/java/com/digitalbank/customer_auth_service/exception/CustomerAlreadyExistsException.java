package com.digitalbank.customer_auth_service.exception;

public class CustomerAlreadyExistsException extends RuntimeException
{
    public CustomerAlreadyExistsException(String message)
    {
        super(message);
    }
}
