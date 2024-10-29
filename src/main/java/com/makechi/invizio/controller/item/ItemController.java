package com.makechi.invizio.controller.item;

import com.makechi.invizio.dto.item.ItemDto;
import com.makechi.invizio.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @PreAuthorize("hasAuthority('item:read')")
    public Page<ItemDto> getAllItems(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        if (query != null && !query.isBlank()) {
            return itemService.getAllItems(page, size, sortBy, sortDirection, query);
        }
        return itemService.getAllItems(page, size, sortBy, sortDirection);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('item:read')")
    public ItemDto getItemById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/sku/{sku}")
    @PreAuthorize("hasAuthority('item:read')")
    public ItemDto getItemBySku(@PathVariable String sku) {
        return itemService.getItemBySku(sku);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('item:create')")
    public ResponseEntity<ItemDto> addItem(@RequestBody AddUpdateItemRequest request) {
        ItemDto item = itemService.addRequest(request);
        return new ResponseEntity<>(item, HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('item:update')")
    public ItemDto updateItem(@PathVariable String id, @RequestBody AddUpdateItemRequest request) {
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('item:delete')")
    public String deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return "Item deleted successfully";
    }
}
