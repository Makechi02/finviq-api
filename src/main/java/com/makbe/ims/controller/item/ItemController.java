package com.makbe.ims.controller.item;

import com.makbe.ims.collections.Item;
import com.makbe.ims.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
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
    public Item addItem(@RequestBody AddUpdateItemRequest request) {
        return itemService.addRequest(request);
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
