package com.makbe.ims.controller.supplier;

import com.makbe.ims.collections.Supplier;
import com.makbe.ims.service.supplier.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable String id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping()
    public Supplier addSupplier(@RequestBody AddUpdateSupplierRequest request) {
        return supplierService.addSupplier(request);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable String id, @RequestBody AddUpdateSupplierRequest request) {
        return supplierService.updateSupplier(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable String id) {
        supplierService.deleteSupplier(id);
        return "Supplier deleted successfully";
    }

}
