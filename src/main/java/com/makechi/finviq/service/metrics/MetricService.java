package com.makechi.finviq.service.metrics;

import com.makechi.finviq.dto.metrics.MetricsDto;
import com.makechi.finviq.dto.metrics.StockLevelDto;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.List;

public interface MetricService {
    MetricsDto getAllMetrics();

    BigDecimal calculateInventoryValuation();

    List<Document> getItemsGroupedByCategory();

    List<StockLevelDto> getStockLevels();
}
