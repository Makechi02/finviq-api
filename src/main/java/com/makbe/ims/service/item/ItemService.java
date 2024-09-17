package com.makbe.ims.service.item;

import com.makbe.ims.collections.Item;
import com.makbe.ims.controller.item.AddUpdateItemRequest;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();

    Item getItemById(String id);

    Item getItemBySku(String sku);

    Item addRequest(AddUpdateItemRequest request);

    Item updateItem(String id, AddUpdateItemRequest request);

    void deleteItem(String id);
}
