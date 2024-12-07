package com.makechi.finviq.service.metrics;

import com.makechi.finviq.collections.item.Item;
import com.makechi.finviq.dto.category.CategoryDtoMapper;
import com.makechi.finviq.dto.category.ModelCategoryDto;
import com.makechi.finviq.dto.item.ItemDto;
import com.makechi.finviq.dto.item.ItemDtoMapper;
import com.makechi.finviq.dto.metrics.MetricsDto;
import com.makechi.finviq.dto.metrics.StockLevelDto;
import com.makechi.finviq.dto.supplier.ModelSupplierDto;
import com.makechi.finviq.dto.supplier.SupplierDtoMapper;
import com.makechi.finviq.dto.user.ModelUserDto;
import com.makechi.finviq.dto.user.UserMapper;
import com.makechi.finviq.repository.CategoryRepository;
import com.makechi.finviq.repository.ItemRepository;
import com.makechi.finviq.repository.SupplierRepository;
import com.makechi.finviq.repository.UserRepository;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MetricsServiceImpl implements MetricService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    private final UserMapper userMapper;
    private final CategoryDtoMapper categoryDtoMapper;
    private final SupplierDtoMapper supplierDtoMapper;
    private final ItemDtoMapper itemDtoMapper;

    private final MongoTemplate mongoTemplate;

    @Override
    public MetricsDto getAllMetrics() {
        long totalUsers = userRepository.count();
        long totalItems = itemRepository.count();
        long totalCategories = categoryRepository.count();
        long totalSuppliers = supplierRepository.count();

        List<ModelUserDto> recentUsers = userRepository
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(user -> userMapper.toModelUserDto(user.getId()))
                .toList();

        List<ItemDto> recentItems = itemRepository.findTop5ByOrderByCreatedAtDesc().stream().map(itemDtoMapper).toList();
        List<ModelCategoryDto> recentCategories = categoryRepository
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(category -> categoryDtoMapper.toModelCategoryDto(category.getId()))
                .toList();
        List<ModelSupplierDto> recentSuppliers = supplierRepository.findTop5ByOrderByAddedAtDesc().stream()
                .map(supplierDtoMapper::toModelSupplierDto).toList();

        return MetricsDto.builder()
                .totalUsers(totalUsers)
                .totalItems(totalItems)
                .totalCategories(totalCategories)
                .totalSuppliers(totalSuppliers)
                .recentUsers(recentUsers)
                .recentItems(recentItems)
                .recentCategories(recentCategories)
                .recentSuppliers(recentSuppliers)
                .build();
    }

    @Override
    public BigDecimal calculateInventoryValuation() {
        BigDecimal totalValuation = BigDecimal.ZERO;

        List<Item> items = itemRepository.findAllForInventoryValuation();

        for (Item item : items) {
            BigDecimal totalItemValue = item.getCostPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalValuation = totalValuation.add(totalItemValue);
        }

        return totalValuation;
    }

    @Override
    public List<StockLevelDto> getStockLevels() {
        List<Item> items = itemRepository.findAllForStockLevels();
        return items.stream()
                .map(item -> StockLevelDto.builder()
                        .name(item.getName())
                        .quantity(item.getQuantity())
                        .stockAlert(item.getStockAlert())
                        .build())
                .toList();
    }

    @Override
    public List<Document> getItemsGroupedByCategory() {
        MongoDatabase database = mongoTemplate.getDb();
        MongoCollection<Document> collection = database.getCollection("items");

        List<Document> aggregationPipeline = Arrays.asList(
                new Document("$group", new Document("_id", "$category")
                        .append("count", new Document("$sum", 1))),
                new Document("$lookup", new Document("from", "categories")
                        .append("localField", "_id")
                        .append("foreignField", "_id")
                        .append("as", "category")),
                new Document("$unwind", new Document("path", "$category")
                        .append("preserveNullAndEmptyArrays", true)),
                new Document("$project", new Document("_id", 1)
                        .append("count", 1)
                        .append("categoryName", new Document("$ifNull", Arrays.asList("$category.name", "Unknown Category"))))
        );

        AggregateIterable<Document> results = collection.aggregate(aggregationPipeline);
        return results.into(new ArrayList<>());
    }
}
