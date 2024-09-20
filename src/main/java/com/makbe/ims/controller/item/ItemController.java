package com.makbe.ims.controller.item;

import com.makbe.ims.collections.Item;
import com.makbe.ims.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Page<Item> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return itemService.getAllItems(page, size, sortBy, sortDirection);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/sku/{sku}")
    public Item getItemBySku(@PathVariable String sku) {
        return itemService.getItemBySku(sku);
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody AddUpdateItemRequest request) {
        Item item = itemService.addRequest(request);
        return new ResponseEntity<>(item, HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody AddUpdateItemRequest request) {
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return "Item deleted successfully";
    }
}
