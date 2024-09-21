package com.makbe.ims.service.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SKUGeneratorTest {

    @Test
    void generateSKU_shouldReturnValidSKU() {
        String sku = SKUGenerator.generateSKU("Laptop", "electronics");
        assertEquals("LAP-ELE", sku.substring(0, 7));
        assertTrue(sku.matches("[A-Z]{3}-[A-Z]{3}-\\d{4}"));
    }

    @Test
    void shouldReturnItemWhenNameIsNull() {
        String sku = SKUGenerator.generateSKU(null, "electronics");
        assertEquals("ITEM-ELE", sku.substring(0, 8));
        assertTrue(sku.matches("ITEM-[A-Z]{3}-\\d{4}"));
    }

    @Test
    void shouldReturnGenWhenCategoryIsNull() {
        String sku = SKUGenerator.generateSKU("laptop", null);
        assertEquals("LAP-GEN", sku.substring(0, 7));
        assertTrue(sku.matches("[A-Z]{3}-GEN-\\d{4}"));
    }

    @Test
    void shouldReturnItemAndGenWhenNameAndCategoryIsNull() {
        String sku = SKUGenerator.generateSKU(null, null);
        assertEquals("ITEM-GEN", sku.substring(0, 8));
        assertTrue(sku.matches("ITEM-GEN-\\d{4}"));
    }

    @Test
    void shouldHandleShortNamesAndCategories() {
        String sku = SKUGenerator.generateSKU("PC", "it");
        assertEquals("PC-IT", sku.substring(0, 5));
        assertTrue(sku.matches("[A-Z]{2}-[A-Z]{2}-\\d{4}"));
    }

    @Test
    void shouldHandleEmptyStringsForNameAndCategory() {
        String sku = SKUGenerator.generateSKU("", "");
        assertEquals("ITEM-GEN", sku.substring(0, 8));
        assertTrue(sku.matches("ITEM-GEN-\\d{4}"));
    }

    @Test
    void shouldHandleBlankStringsForNameAndCategory() {
        String sku = SKUGenerator.generateSKU(" ", " ");
        assertEquals("ITEM-GEN", sku.substring(0, 8));
        assertTrue(sku.matches("ITEM-GEN-\\d{4}"));
    }

    @Test
    void shouldGenerateUniqueSKUsForSameInputs() {
        String sku1 = SKUGenerator.generateSKU("Laptop", "electronics");
        String sku2 = SKUGenerator.generateSKU("Laptop", "electronics");
        assertNotEquals(sku1, sku2);
    }

    @Test
    void shouldGenerateCorrectLengthSKU() {
        String sku = SKUGenerator.generateSKU("Laptop", "electronics");
        assertEquals(12, sku.length());
    }

    @Test
    void randomCodeShouldBeExactlyFourDigits() {
        String sku = SKUGenerator.generateSKU("Laptop", "electronics");
        String randomCode = sku.substring(8);
        assertTrue(randomCode.matches("\\d{4}"));
    }
}