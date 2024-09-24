package com.makbe.ims.service.supplier;

import com.makbe.ims.collections.Supplier;
import com.makbe.ims.controller.supplier.AddUpdateSupplierRequest;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();

    List<Supplier> getAllSuppliers(String query);

    Supplier getSupplierById(String id);

    Supplier addSupplier(AddUpdateSupplierRequest request);

    Supplier updateSupplier(String id, AddUpdateSupplierRequest request);

    void deleteSupplier(String id);

}
