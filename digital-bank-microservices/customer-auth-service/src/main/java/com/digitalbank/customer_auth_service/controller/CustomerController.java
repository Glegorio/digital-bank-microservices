package com.digitalbank.customer_auth_service.controller;

import com.digitalbank.customer_auth_service.dto.request.CustomerRequest;
import com.digitalbank.customer_auth_service.dto.response.CustomerResponse;
import com.digitalbank.customer_auth_service.repository.CustomerRepository;
import com.digitalbank.customer_auth_service.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description = "Gerenciamento de clientes")
public class CustomerController {

    private final CustomerService service;
    public CustomerController(CustomerService customerService) {
        this.service = customerService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar Cliente", description = "Realiza o cadastro de um novo cliente")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request){
        CustomerResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "Listar Clientes", description = "Retorna todos os clientes cadastrados")
    public ResponseEntity<List<CustomerResponse>> findAll(){
        List<CustomerResponse> customers = service.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Localiza um cliente através do identificador")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id){
        CustomerResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Localiza um cliente através do CPF")
    public ResponseEntity<CustomerResponse> findByCpf(@PathVariable String cpf){
        CustomerResponse response = service.findByCpf(cpf);
        return ResponseEntity.ok(response);
    }
}
