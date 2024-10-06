package com.makbe.ims.service.supplier;

import com.makbe.ims.collections.Supplier;
import com.makbe.ims.controller.supplier.AddUpdateSupplierRequest;
import com.makbe.ims.dto.supplier.SupplierDto;
import com.makbe.ims.dto.supplier.SupplierDtoMapper;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.exception.RequestValidationException;
import com.makbe.ims.exception.ResourceNotFoundException;
import com.makbe.ims.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierDtoMapper supplierDtoMapper;

    @Override
    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        log.info("All suppliers: {}", suppliers.size());
        return suppliers.stream().map(supplierDtoMapper).toList();
    }

    @Override
    public List<SupplierDto> getAllSuppliers(String query) {
        List<Supplier> suppliers = supplierRepository.searchByKeyword(query);
        log.info("Search query: {}", query);
        log.info("Total suppliers found: {}", suppliers.size());
        return suppliers.stream().map(supplierDtoMapper).toList();
    }

    @Override
    public SupplierDto getSupplierById(String id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));
        return supplierDtoMapper.apply(supplier);
    }

    @Override
    public SupplierDto addSupplier(AddUpdateSupplierRequest request) {
        if (supplierRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("Supplier with phone " + request.phone() + " already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.name())
                .address(request.address())
                .phone(request.phone())
                .build();
        supplier = supplierRepository.save(supplier);
        return supplierDtoMapper.apply(supplier);
    }

    @Override
    public SupplierDto updateSupplier(String id, AddUpdateSupplierRequest request) {
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
        return supplierDtoMapper.apply(supplier);
    }

    @Override
    public void deleteSupplier(String id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier with id " + id + " not found");
        }

        supplierRepository.deleteById(id);
    }
}
