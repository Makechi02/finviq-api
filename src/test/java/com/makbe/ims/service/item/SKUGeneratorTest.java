package com.makbe.ims.service.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SKUGeneratorTest {

    @Test
    void generateSKU() {
        String sku = SKUGenerator.generateSKU("Laptop", "electronics");
        assertEquals("LAP-ELE", sku.substring(0, 7));
    }

    @Test
    void shouldReturnItemWhenNameIsNull() {
        String sku = SKUGenerator.generateSKU(null, "electronics");
        assertEquals("ITEM-ELE", sku.substring(0, 8));
    }

    @Test
    void shouldReturnGenWhenCategoryIsNull() {
        String sku = SKUGenerator.generateSKU("laptop", null);
        assertEquals("LAP-GEN", sku.substring(0, 7));
    }

    @Test
    void shouldReturnItemAndGenWhenNameAndCategoryIsNull() {
        String sku = SKUGenerator.generateSKU(null, null);
        assertEquals("ITEM-GEN", sku.substring(0, 8));
    }
}