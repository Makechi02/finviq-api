package com.makbe.ims.controller.customer;

public record AddUpdateCustomerRequest(String name, String contactPerson, String email, String phone, String address) {
}
