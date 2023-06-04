package com.fluidnotions.components.fake.resolvers;

import com.fluidnotions.databases.fake.FakeBookDataSource;
import com.fluidnotions.databases.fake.FakeShowDataSource;
import com.fluidnotions.graphql.generated.types.Book;
import com.fluidnotions.graphql.generated.types.Show;
import com.fluidnotions.graphql.generated.types.SmartSearchResults;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class SmartSearchResolver {
    private static final Logger logger = LoggerFactory.getLogger(SmartSearchResolver.class);

    @DgsQuery
    public List<SmartSearchResults> smartSearch(@InputArgument Optional<String> keyword) {
        var results = new ArrayList<SmartSearchResults>();
        List<Book> bookList = FakeBookDataSource.BOOK_LIST;
        List<Show> showList = FakeShowDataSource.SHOWS;
        if (keyword.isEmpty()) {
            bookList.stream().forEach(o -> results.add(o));
            showList.stream().forEach(o -> results.add(o));
        } else {
            bookList.stream().filter(o -> o.toString().contains(keyword.get())).forEach(o -> results.add(o));
            showList.stream().filter(o -> o.toString().contains(keyword.get())).forEach(o -> results.add(o));
        }
        return results;
    }
}
