package com.makechi.invizio.controller.metrics;

import com.makechi.invizio.dto.metrics.MetricsDto;
import com.makechi.invizio.dto.metrics.StockLevelDto;
import com.makechi.invizio.service.metrics.MetricService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricService metricService;

    @GetMapping
    public MetricsDto getAllMetrics() {
        return metricService.getAllMetrics();
    }

    @GetMapping("/inventory-valuation")
    public BigDecimal getInventoryValuation() {
        return metricService.calculateInventoryValuation();
    }

    @GetMapping("/items-by-category")
    public List<Document> getItemsGroupedByCategory() {
        return metricService.getItemsGroupedByCategory();
    }

    @GetMapping("/stock-levels")
    public List<StockLevelDto> getStockLevels() {
        return metricService.getStockLevels();
    }

}
