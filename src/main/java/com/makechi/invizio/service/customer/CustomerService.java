package com.makechi.invizio.service.customer;

import com.makechi.invizio.controller.customer.AddUpdateCustomerRequest;
import com.makechi.invizio.dto.customer.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(String id);

    List<CustomerDto> findCustomersByName(String name);

    CustomerDto addCustomer(AddUpdateCustomerRequest request);

    CustomerDto updateCustomer(String id, AddUpdateCustomerRequest request);

    void deleteCustomer(String id);
}
