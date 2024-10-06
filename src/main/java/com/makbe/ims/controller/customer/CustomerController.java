package com.makbe.ims.controller.customer;

import com.makbe.ims.dto.customer.CustomerDto;
import com.makbe.ims.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/search")
    public List<CustomerDto> searchCustomers(@RequestParam String name) {
        return customerService.findCustomersByName(name);
    }

    @PostMapping
    public CustomerDto addCustomer(@RequestBody AddUpdateCustomerRequest request) {
        return customerService.addCustomer(request);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable String id, @RequestBody AddUpdateCustomerRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
