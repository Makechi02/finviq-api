package com.makechi.invizio.service.item;

import com.makechi.invizio.collections.item.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemMigrationService {

    private static final BigDecimal VAT_RATE = new BigDecimal("0.16");

    private final MongoTemplate mongoTemplate;

    public void migrateItems() {
        mongoTemplate.findAll(Item.class).forEach(item -> {
            if (item.getCostPrice() == null && item.getRetailPrice() == null && item.getVatInclusivePrice() == null) {
                BigDecimal price = BigDecimal.valueOf(item.getPrice());
                if (price != null) {
                    BigDecimal vatInclusivePrice = price.add(price.multiply(VAT_RATE));

                    item.setCostPrice(price);
                    item.setRetailPrice(price);
                    item.setVatInclusivePrice(vatInclusivePrice);

                    item.setPrice(0);

                    mongoTemplate.save(item);

                    log.info("Migrated item with ID: {}", item.getId());
                }
            } else {
                log.info("Skipping item with ID: {} (already migrated or missing price)", item.getId());
            }
        });
    }
}
