package com.fluidnotions.components.fake.resolvers;

import com.fluidnotions.databases.fake.FakeBookDataSource;
import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.Book;
import com.fluidnotions.graphql.generated.types.BookList;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
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

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByReleased
    )
    public BookList getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
        @SuppressWarnings("unchecked")
        var map = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
        var printed = (boolean) map.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition);
        var year = (int) map.get(DgsConstants.RELEASEHISTORYINPUT.Year);
        logger.trace("printed: " + printed + ", year: " + year);
        List<Book> filtered = FakeBookDataSource.BOOK_LIST.stream().filter(book -> {
            boolean isPrinted = book.getReleased().getPrintedEdition();
            int releaseYear = book.getReleased().getYear();
            return isPrinted == printed && releaseYear >= year;
        }).collect(Collectors.toList());
        return BookList.newBuilder().books(filtered).totalCount(filtered.size()).build();
    }
}
