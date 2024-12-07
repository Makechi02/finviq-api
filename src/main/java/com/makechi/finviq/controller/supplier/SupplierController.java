package com.makechi.finviq.controller.supplier;

import com.makechi.finviq.dto.supplier.SupplierDto;
import com.makechi.finviq.service.supplier.SupplierService;
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
    public List<SupplierDto> getAllSuppliers(@RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.isBlank()) {
            return supplierService.getAllSuppliers(query);
        }
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierDto getSupplierById(@PathVariable String id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping()
    public SupplierDto addSupplier(@RequestBody AddUpdateSupplierRequest request) {
        return supplierService.addSupplier(request);
    }

    @PutMapping("/{id}")
    public SupplierDto updateSupplier(@PathVariable String id, @RequestBody AddUpdateSupplierRequest request) {
        return supplierService.updateSupplier(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable String id) {
        supplierService.deleteSupplier(id);
        return "Supplier deleted successfully";
    }

}
