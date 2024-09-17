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
        if (supplierRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("Supplier with phone " + request.phone() + " already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.name())
                .address(request.address())
                .phone(request.phone())
                .build();
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(String id, AddUpdateSupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));

        boolean changes = false;

        if (request.name() != null && !request.name().isBlank() && !request.name().equals(supplier.getName())) {
            supplier.setName(request.name());
            changes = true;
        }

        if (request.address() != null && !request.address().isBlank() && !request.address().equals(supplier.getAddress())) {
            supplier.setAddress(request.address());
            changes = true;
        }

        if (request.phone() != null && !request.phone().isBlank() && !request.phone().equals(supplier.getPhone())) {
            if (supplierRepository.existsByPhone(request.phone())) {
                throw new DuplicateResourceException("Supplier with phone " + request.phone() + " already exists");
            }

            supplier.setPhone(request.phone());
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
