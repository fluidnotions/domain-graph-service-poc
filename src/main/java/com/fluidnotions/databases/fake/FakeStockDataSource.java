package com.fluidnotions.databases.fake;

import com.fluidnotions.graphql.generated.types.Stock;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;

@Configuration
public class FakeStockDataSource {
    private static final Logger logger = LoggerFactory.getLogger(FakeStockDataSource.class);

    @Autowired
    private Faker faker;

    public Stock randomStock() {
        return Stock.newBuilder().lastTradeDateTime(OffsetDateTime.now().toLocalDateTime())
                .price(faker.random().nextInt(100, 1000))
                .symbol(faker.stock().nyseSymbol())
                .build();
    }
}
