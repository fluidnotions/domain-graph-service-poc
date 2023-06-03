package com.fluidnotions.resolvers;

import com.fluidnotions.databases.fake.FakeStockDataSource;
import com.fluidnotions.graphql.generated.types.Stock;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
public class StockResolver {
    private static final Logger logger = LoggerFactory.getLogger(StockResolver.class);

    @Autowired
    private FakeStockDataSource dataSource;

    @DgsSubscription
    public Publisher<Stock> randomStock() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> dataSource.randomStock());
    }

}
