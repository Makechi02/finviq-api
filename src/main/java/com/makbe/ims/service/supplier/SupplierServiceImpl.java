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
        if (supplierRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Supplier with phone " + request.getPhone() + " already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
        supplier = supplierRepository.save(supplier);
        return supplierDtoMapper.apply(supplier);
    }

    @Override
    public SupplierDto updateSupplier(String id, AddUpdateSupplierRequest request) {
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
