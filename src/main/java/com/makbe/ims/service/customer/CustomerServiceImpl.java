package com.makbe.ims.service.customer;

import com.makbe.ims.collections.Customer;
import com.makbe.ims.controller.customer.AddUpdateCustomerRequest;
import com.makbe.ims.dto.customer.CustomerDto;
import com.makbe.ims.dto.customer.CustomerDtoMapper;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.exception.RequestValidationException;
import com.makbe.ims.exception.ResourceNotFoundException;
import com.makbe.ims.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerDtoMapper)
                .toList();
    }

    @Override
    public CustomerDto getCustomerById(String id) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));
        return customerDtoMapper.apply(customer);
    }

    @Override
    public List<CustomerDto> findCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(customerDtoMapper)
                .toList();
    }

    @Override
    public CustomerDto addCustomer(AddUpdateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email()))
            throw new DuplicateResourceException("Customer with email " + request.email() + " already exists");

        var customer = Customer.builder()
                .name(request.name())
                .contactPerson(request.contactPerson())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .build();

        customer = customerRepository.save(customer);
        return customerDtoMapper.apply(customer);
    }

    @Override
    public CustomerDto updateCustomer(String id, AddUpdateCustomerRequest request) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

        boolean changes = false;

        if (request.name() != null && !request.name().isBlank() && !request.name().equals(customer.getName())) {
            customer.setName(request.name());
            changes = true;
        }

        if (request.contactPerson() != null && !request.contactPerson().isBlank() && !request.contactPerson().equals(customer.getContactPerson())) {
            customer.setContactPerson(request.contactPerson());
            changes = true;
        }

        if (request.email() != null && !request.email().isBlank() && !request.email().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(request.email()))
                throw new RequestValidationException("Customer with email " + request.email() + " already exists");

            customer.setEmail(request.email());
            changes = true;
        }

        if (request.phone() != null && !request.phone().isBlank() && !request.phone().equals(customer.getPhone())) {
            customer.setPhone(request.phone());
            changes = true;
        }

        if (request.address() != null && !request.address().isBlank() && !request.address().equals(customer.getAddress())) {
            customer.setAddress(request.address());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes");
        }

        customer = customerRepository.save(customer);
        return customerDtoMapper.apply(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id))
            throw new ResourceNotFoundException("Customer with id " + id + " not found");

        customerRepository.deleteById(id);
    }
}
