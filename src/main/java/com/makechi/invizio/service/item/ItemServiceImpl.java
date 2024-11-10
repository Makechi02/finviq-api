package com.makechi.invizio.service.item;

import com.makechi.invizio.collections.Category;
import com.makechi.invizio.collections.item.Item;
import com.makechi.invizio.controller.item.AddUpdateItemRequest;
import com.makechi.invizio.dto.item.ItemDto;
import com.makechi.invizio.dto.item.ItemDtoMapper;
import com.makechi.invizio.exception.DuplicateResourceException;
import com.makechi.invizio.exception.RequestValidationException;
import com.makechi.invizio.exception.ResourceNotFoundException;
import com.makechi.invizio.repository.CategoryRepository;
import com.makechi.invizio.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemDtoMapper itemDtoMapper;

    private static final BigDecimal VAT_RATE = new BigDecimal("0.16");

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
        return itemRepository
                .findById(id)
                .map(itemDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));
    }

    @Override
    public ItemDto getItemBySku(String sku) {
        return itemRepository
                .findBySku(sku)
                .map(itemDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Item with SKU " + sku + " not found"));
    }

    @Override
    public ItemDto addItem(AddUpdateItemRequest request) {
        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategory() + " not found"));

        BigDecimal vatInclusivePrice = BigDecimal.ZERO;
        String sku;

        if (itemRepository.existsByName(request.getName())
                && itemRepository.existsByBrand(request.getBrand())
                && itemRepository.existsByModel(request.getModel())) {
            throw new DuplicateResourceException("Item already exists");
        }

        if (request.getRetailPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RequestValidationException("Item retail price can't be zero or below");
        }

        if (request.getCostPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RequestValidationException("Item cost price can't be zero or below");
        }

        if (request.getVatInclusivePrice() == null || request.getVatInclusivePrice().compareTo(BigDecimal.ZERO) <= 0) {
            vatInclusivePrice = request.getRetailPrice().add(request.getRetailPrice().multiply(VAT_RATE));
        }

        if (request.getQuantity() <= 0) {
            throw new RequestValidationException("Item quantity can't be zero or below");
        }

        if (request.getStockAlert() <= 0) {
            throw new RequestValidationException("Item stock alert can't be zero or below");
        }

        if (request.getSku() == null || request.getSku().isBlank()) {
            sku = SKUGenerator.generateSKU(request.getName(), category.getName());
        } else {
            sku = request.getSku();
        }

        Item item = Item.builder()
                .brand(request.getBrand())
                .category(new ObjectId(request.getCategory()))
                .model(request.getModel())
                .name(request.getName())
                .costPrice(request.getCostPrice())
                .retailPrice(request.getRetailPrice())
                .vatInclusivePrice(vatInclusivePrice)
                .quantity(request.getQuantity())
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

        if (request.getCostPrice() != null && !Objects.equals(request.getCostPrice(), item.getCostPrice())) {
            if (request.getCostPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RequestValidationException("Item cost price can't be zero or below");
            }
            item.setCostPrice(request.getCostPrice());
            changes = true;
        }

        if (request.getRetailPrice() != null && !Objects.equals(request.getRetailPrice(), item.getRetailPrice())) {
            if (request.getRetailPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RequestValidationException("Item retail price can't be zero or below");
            }
            item.setRetailPrice(request.getRetailPrice());
            changes = true;
        }

        if (request.getVatInclusivePrice() != null && !Objects.equals(request.getVatInclusivePrice(), item.getVatInclusivePrice())) {
            if (request.getVatInclusivePrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RequestValidationException("Item VAT inclusive price can't be zero or below");
            }
            item.setVatInclusivePrice(request.getVatInclusivePrice());
            changes = true;
        }

        if (request.getQuantity() != item.getQuantity()) {
            if (request.getQuantity() <= 0) {
                throw new RequestValidationException("Item quantity can't be zero or below");
            }
            item.setQuantity(request.getQuantity());
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

    @Override
    public void increaseStock(String id, int quantity) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));

        item.setQuantity(item.getQuantity() + quantity);
        itemRepository.save(item);
    }

    @Override
    public void decreaseStock(String id, int quantity) {
        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + " not found"));

        if (item.getQuantity() < quantity) {
            throw new RequestValidationException("Insufficient stock");
        }
        item.setQuantity(item.getQuantity() - quantity);
        itemRepository.save(item);
    }
}
