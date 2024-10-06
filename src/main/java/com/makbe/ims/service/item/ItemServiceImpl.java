package com.makbe.ims.service.item;

import com.makbe.ims.collections.Category;
import com.makbe.ims.collections.Item;
import com.makbe.ims.controller.item.AddUpdateItemRequest;
import com.makbe.ims.dto.item.ItemDto;
import com.makbe.ims.dto.item.ItemDtoMapper;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.exception.RequestValidationException;
import com.makbe.ims.exception.ResourceNotFoundException;
import com.makbe.ims.repository.CategoryRepository;
import com.makbe.ims.repository.ItemRepository;
import com.makbe.ims.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ItemDtoMapper itemDtoMapper;

    @Override
    public Page<ItemDto> getAllItems(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Item> items = itemRepository.findAll(pageRequest);
        log.info("All items: {}", items.getTotalElements());
        return items.map(itemDtoMapper);
    }

    @Override
    public Page<ItemDto> getAllItems(int page, int size, String sortBy, String sortDirection, String query) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        log.info("Search query: {}", query);
        Page<Item> items = itemRepository.searchByKeyword(query, pageRequest);
        log.info("Total items found: {}", items.getTotalElements());
        return items.map(itemDtoMapper);
    }

    @Override
    public ItemDto getItemById(String id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));
        return itemDtoMapper.apply(item);
    }

    @Override
    public ItemDto getItemBySku(String sku) {
        Item item = itemRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Item with SKU " + sku + " not found"));
        return itemDtoMapper.apply(item);
    }

    @Override
    public ItemDto addRequest(AddUpdateItemRequest request) {
        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategory() + " not found"));

        if (!supplierRepository.existsById(request.getSupplier())) {
            throw new ResourceNotFoundException("Supplier with id " + request.getSupplier() + " not found");
        }

        if (itemRepository.existsByName(request.getName())
                && itemRepository.existsByBrand(request.getBrand())
                && itemRepository.existsByModel(request.getModel())) {
            throw new DuplicateResourceException("Item already exists");
        }

        if (request.getPrice() <= 0) {
            throw new RequestValidationException("Item price can't be zero or below");
        }

        if (request.getQuantity() <= 0) {
            throw new RequestValidationException("Item quantity can't be zero or below");
        }

        if (request.getStockAlert() <= 0) {
            throw new RequestValidationException("Item stock alert can't be zero or below");
        }

        String sku = SKUGenerator.generateSKU(request.getName(), category.getName());

        Item item = Item.builder()
                .brand(request.getBrand())
                .category(new ObjectId(request.getCategory()))
                .model(request.getModel())
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .supplier(new ObjectId(request.getSupplier()))
                .stockAlert(request.getStockAlert())
                .sku(sku)
                .build();

        item = itemRepository.save(item);
        return itemDtoMapper.apply(item);
    }

    @Override
    public ItemDto updateItem(String id, AddUpdateItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));

        boolean changes = false;

        if (request.getBrand() != null && !request.getBrand().isBlank() && !request.getBrand().equals(item.getBrand())) {
            item.setBrand(request.getBrand());
            changes = true;
        }

        if (request.getCategory() != null && !request.getCategory().isBlank() && !new ObjectId(request.getCategory()).equals(item.getCategory())) {
            Category category = categoryRepository.findById(request.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategory() + " not found"));

            item.setCategory(new ObjectId(category.getId()));
            changes = true;
        }

        if (request.getModel() != null && !request.getModel().isBlank() && !request.getModel().equals(item.getModel())) {
            item.setModel(request.getModel());
            changes = true;
        }

        if (request.getName() != null && !request.getName().isBlank() && !request.getName().equals(item.getName())) {
            item.setName(request.getName());
            changes = true;
        }

        if (request.getPrice() != item.getPrice()) {
            if (request.getPrice() <= 0) {
                throw new RequestValidationException("Item price can't be zero or below");
            }
            item.setPrice(request.getPrice());
            changes = true;
        }

        if (request.getQuantity() != item.getQuantity()) {
            if (request.getQuantity() <= 0) {
                throw new RequestValidationException("Item quantity can't be zero or below");
            }
            item.setQuantity(request.getQuantity());
            changes = true;
        }

        if (request.getSupplier() != null && !request.getSupplier().isBlank() && !new ObjectId(request.getSupplier()).equals(item.getSupplier())) {
            if (!supplierRepository.existsById(request.getSupplier())) {
                throw new ResourceNotFoundException("Supplier with id " + request.getSupplier() + " not found");
            }

            item.setSupplier(new ObjectId(request.getSupplier()));
            changes = true;
        }

        if (request.getStockAlert() != item.getStockAlert()) {
            if (request.getStockAlert() <= 0) {
                throw new RequestValidationException("Item stock alert can't be zero or below");
            }
            item.setStockAlert(request.getStockAlert());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes");
        }

        item = itemRepository.save(item);
        return itemDtoMapper.apply(item);
    }

    @Override
    public void deleteItem(String id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item with id " + id + " not found");
        }

        itemRepository.deleteById(id);
    }
}
