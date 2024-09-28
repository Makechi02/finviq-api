package com.makbe.ims.service.metrics;

import com.makbe.ims.dto.metrics.MetricsDto;
import com.makbe.ims.dto.metrics.StockLevelDto;
import org.bson.Document;

import java.util.List;

public interface MetricService {
    MetricsDto getAllMetrics();

    double calculateInventoryValuation();

    List<Document> getItemsGroupedByCategory();

    List<StockLevelDto> getStockLevels();
}
