package com.fluidnotions.resolvers;

import com.fluidnotions.databases.fake.FakeBookDataSource;
import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.types.Book;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class BookResolver {
    private static final Logger logger = LoggerFactory.getLogger(BookResolver.class);

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "books")
    public List<Book> booksByAuthor(@InputArgument(name = "authorFilter") Optional<String> filterText) {
        var books = FakeBookDataSource.BOOK_LIST;
        if (filterText.isEmpty() || filterText.get().isEmpty()) {
            return books;
        }
        return books.stream().filter(s -> {
            var authorName = s.getAuthor().getName();
            var filter = filterText.get();
            logger.trace("authorName: " + authorName + ", filter: " + filter);
            return authorName.contains(filter);
        }).collect(Collectors.toList());
    }
}
