package com.makbe.ims.service.item;

import com.makbe.ims.collections.Item;
import com.makbe.ims.controller.item.AddUpdateItemRequest;
import org.springframework.data.domain.Page;

public interface ItemService {
    Page<Item> getAllItems(int page, int size, String sortBy, String sortDirection);

    Item getItemById(String id);

    Item getItemBySku(String sku);

    Item addRequest(AddUpdateItemRequest request);

    Item updateItem(String id, AddUpdateItemRequest request);

    void deleteItem(String id);
}
