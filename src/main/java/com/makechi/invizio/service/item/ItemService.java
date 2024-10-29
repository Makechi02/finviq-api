package com.makechi.invizio.service.item;

import com.makechi.invizio.controller.item.AddUpdateItemRequest;
import com.makechi.invizio.dto.item.ItemDto;
import org.springframework.data.domain.Page;

public interface ItemService {
    Page<ItemDto> getAllItems(int page, int size, String sortBy, String sortDirection);

    Page<ItemDto> getAllItems(int page, int size, String sortBy, String sortDirection, String query);

    ItemDto getItemById(String id);

    ItemDto getItemBySku(String sku);

    ItemDto addRequest(AddUpdateItemRequest request);

    ItemDto updateItem(String id, AddUpdateItemRequest request);

    void deleteItem(String id);

    void increaseStock(String id, int quantity);

    void decreaseStock(String id, int quantity);
}
