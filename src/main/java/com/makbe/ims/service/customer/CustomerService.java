package com.makbe.ims.service.customer;

import com.makbe.ims.controller.customer.AddUpdateCustomerRequest;
import com.makbe.ims.dto.customer.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(String id);

    List<CustomerDto> findCustomersByName(String name);

    CustomerDto addCustomer(AddUpdateCustomerRequest request);

    CustomerDto updateCustomer(String id, AddUpdateCustomerRequest request);

    void deleteCustomer(String id);
}
