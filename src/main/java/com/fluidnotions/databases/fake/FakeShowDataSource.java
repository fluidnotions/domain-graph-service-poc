package com.fluidnotions.databases.fake;

import com.fluidnotions.graphql.generated.types.Show;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakeShowDataSource {
    private static final Logger logger = LoggerFactory.getLogger(FakeShowDataSource.class);

    public static final List<Show> SHOWS = new ArrayList<>();

    @PostConstruct
    void postConstruct() {
        List.of(
                new Show("Stranger Things", 2016, null),
                new Show("Ozark", 2017, null),
                new Show("The Crown", 2016, null),
                new Show("Dead to Me", 2019, null),
                new Show("Orange is the New Black", 2013, null)
        ).stream().forEach(SHOWS::add);
    }
}
