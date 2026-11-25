package com.digitalbank.customer_auth_service.service;

import com.digitalbank.customer_auth_service.dto.request.CustomerRequest;
import com.digitalbank.customer_auth_service.dto.response.CustomerResponse;
import com.digitalbank.customer_auth_service.exception.CustomerAlreadyExistsException;
import com.digitalbank.customer_auth_service.exception.CustomerNotFoundException;
import com.digitalbank.customer_auth_service.model.Customer;
import com.digitalbank.customer_auth_service.model.CustomerStatus;
import com.digitalbank.customer_auth_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll(){
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Cliente não encontrado com ID: " + id
                ));
        return toResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findByCpf(String cpf){
        Customer customer = customerRepository.findByCpf(cpf)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Cliente não encontrado com CPF: " + cpf
                ));
        return toResponse(customer);
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
