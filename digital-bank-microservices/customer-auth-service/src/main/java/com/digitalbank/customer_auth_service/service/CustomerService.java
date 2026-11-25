package com.digitalbank.customer_auth_service.service;

import com.digitalbank.customer_auth_service.dto.request.CustomerRequest;
import com.digitalbank.customer_auth_service.dto.response.CustomerResponse;
import com.digitalbank.customer_auth_service.exception.CustomerAlreadyExistsException;
import com.digitalbank.customer_auth_service.model.Customer;
import com.digitalbank.customer_auth_service.model.CustomerStatus;
import com.digitalbank.customer_auth_service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request){

        if(customerRepository.existsByCpf(request.cpf())){
            throw new CustomerAlreadyExistsException(
                    "Já existe um cliente cadastrado com esse CPF"
            );
        }

        if(customerRepository.existsByEmail(request.email())){
            throw new CustomerAlreadyExistsException(
                    "Já existe um cliente cadastrado com esse e-mail"
            );
        }

        Customer customer = new Customer();

        customer.setFullName(request.fullName());
        customer.setCpf(request.cpf());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setBirthDate(request.birthDate());
        customer.setStatus(CustomerStatus.PENDING);

        Customer savedCustomer = customerRepository.save(customer);

        return toResponse(savedCustomer);
    }

    private CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getBirthDate(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }
}
