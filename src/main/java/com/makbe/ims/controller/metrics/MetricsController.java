package com.makbe.ims.controller.metrics;

import com.makbe.ims.dto.metrics.MetricsDto;
import com.makbe.ims.dto.metrics.StockLevelDto;
import com.makbe.ims.service.metrics.MetricService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public double getInventoryValuation() {
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
