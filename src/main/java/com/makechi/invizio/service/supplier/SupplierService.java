package com.makechi.invizio.service.supplier;

import com.makechi.invizio.controller.supplier.AddUpdateSupplierRequest;
import com.makechi.invizio.dto.supplier.SupplierDto;

import java.util.List;

public interface SupplierService {
    List<SupplierDto> getAllSuppliers();

    List<SupplierDto> getAllSuppliers(String query);

    SupplierDto getSupplierById(String id);

    SupplierDto addSupplier(AddUpdateSupplierRequest request);

    SupplierDto updateSupplier(String id, AddUpdateSupplierRequest request);

    void deleteSupplier(String id);

}
