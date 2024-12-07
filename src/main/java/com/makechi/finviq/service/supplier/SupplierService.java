package com.makechi.finviq.service.supplier;

import com.makechi.finviq.controller.supplier.AddUpdateSupplierRequest;
import com.makechi.finviq.dto.supplier.SupplierDto;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> getAllSuppliers();

    List<SupplierDto> getAllSuppliers(String query);

    SupplierDto getSupplierById(String id);

    SupplierDto addSupplier(AddUpdateSupplierRequest request);

    SupplierDto updateSupplier(String id, AddUpdateSupplierRequest request);

    void deleteSupplier(String id);

}
