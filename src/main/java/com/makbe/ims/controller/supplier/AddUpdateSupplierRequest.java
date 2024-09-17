package com.makbe.ims.controller.supplier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddUpdateSupplierRequest {
    private String name;
    private String address;
    private String phone;
}
