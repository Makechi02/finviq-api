package com.makbe.ims.dto.order;

import java.math.BigDecimal;

public record ItemOrderDto(String itemId, String name, int quantity, BigDecimal price) {
}
