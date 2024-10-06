package com.makbe.ims.service.supplier;

import com.makbe.ims.controller.supplier.AddUpdateSupplierRequest;
import com.makbe.ims.dto.supplier.SupplierDto;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> getAllSuppliers();

    List<SupplierDto> getAllSuppliers(String query);

    SupplierDto getSupplierById(String id);

    SupplierDto addSupplier(AddUpdateSupplierRequest request);

    SupplierDto updateSupplier(String id, AddUpdateSupplierRequest request);

    void deleteSupplier(String id);

}
