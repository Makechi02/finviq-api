package com.makbe.ims.service.item;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SKUGenerator {
    public static String generateSKU(String name, String category) {
        String prefix = name != null ? name.substring(0, Math.min(3, name.length())).toUpperCase() : "ITEM";
        String categoryCode = category != null ? category.substring(0, 3).toUpperCase() : "GEN";
        String randomCode = String.format("%04d", new Random().nextInt(10000));
        return String.format("%s-%s-%s", prefix, categoryCode, randomCode);
    }
}
