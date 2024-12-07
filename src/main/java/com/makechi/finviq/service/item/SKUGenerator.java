package com.makechi.finviq.service.item;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SKUGenerator {
    public static String generateSKU(String name, String category) {
        String prefix = name != null && !name.isBlank() ? name.substring(0, Math.min(3, name.length())).toUpperCase() : "ITEM";
        String categoryCode = category != null && !category.isBlank() ? category.substring(0, Math.min(3, category.length())).toUpperCase() : "GEN";
        String randomCode = String.format("%04d", new Random().nextInt(10000));
        return String.format("%s-%s-%s", prefix.trim(), categoryCode.trim(), randomCode);
    }
}
