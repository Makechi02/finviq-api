package com.makechi.invizio;

import com.makechi.invizio.service.item.ItemMigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MigrationRunner implements CommandLineRunner {

    private final ItemMigrationService itemMigrationService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting migration...");
        itemMigrationService.migrateItems();
        log.info("Migration completed.");
    }
}
