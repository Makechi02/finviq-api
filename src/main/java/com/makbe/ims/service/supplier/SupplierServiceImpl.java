package com.makbe.ims.service.supplier;

import com.makbe.ims.collections.Supplier;
import com.makbe.ims.controller.supplier.AddUpdateSupplierRequest;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.exception.RequestValidationException;
import com.makbe.ims.exception.ResourceNotFoundException;
import com.makbe.ims.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(String id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));
    }

    @Override
    public Supplier addSupplier(AddUpdateSupplierRequest request) {
        if (supplierRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Supplier with phone " + request.getPhone() + " already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(String id, AddUpdateSupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));

        boolean changes = false;

        if (request.getName() != null && !request.getName().isBlank() && !request.getName().equals(supplier.getName())) {
            supplier.setName(request.getName());
            changes = true;
        }

        if (request.getAddress() != null && !request.getAddress().isBlank() && !request.getAddress().equals(supplier.getAddress())) {
            supplier.setAddress(request.getAddress());
            changes = true;
        }

        if (request.getPhone() != null && !request.getPhone().isBlank() && !request.getPhone().equals(supplier.getPhone())) {
            if (supplierRepository.existsByPhone(request.getPhone())) {
                throw new DuplicateResourceException("Supplier with phone " + request.getPhone() + " already exists");
            }

            supplier.setPhone(request.getPhone());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes");
        }

        supplier = supplierRepository.save(supplier);
        return supplier;
    }

    @Override
    public void deleteSupplier(String id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier with id " + id + " not found");
        }

        supplierRepository.deleteById(id);
    }
}
