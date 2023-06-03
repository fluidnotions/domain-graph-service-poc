package com.fluidnotions;

import com.fluidnotions.graphql.generated.client.BooksByReleasedGraphQLQuery;
import com.fluidnotions.graphql.generated.client.BooksByReleasedProjectionRoot;
import com.fluidnotions.graphql.generated.client.BooksGraphQLQuery;
import com.fluidnotions.graphql.generated.client.BooksProjectionRoot;
import com.fluidnotions.graphql.generated.types.Author;
import com.fluidnotions.graphql.generated.types.ReleaseHistoryInput;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import net.datafaker.Faker;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookResolverTest {

    private static final Logger logger = LoggerFactory.getLogger(BookResolverTest.class);

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Autowired
    private Faker faker;

    @Test
    void testAllBooks() {
        var graphqlQuery = new BooksGraphQLQuery.Builder().build();
        var projectionRoot = new BooksProjectionRoot().title()
                .author().name()
                .originCountry()
                .getRoot().released().year();
        var graphqlQueryRequest = new GraphQLQueryRequest(graphqlQuery, projectionRoot).serialize();
        logger.debug("graphqlQueryRequest: {}", graphqlQueryRequest);
        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQueryRequest, "data.books[*].title");

        assertNotNull(titles);
        assertFalse(titles.isEmpty());

        List<Author> authors = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                graphqlQueryRequest, "data.books[*].author",
                new TypeRef<List<Author>>() {
                }
        );

        assertNotNull(authors);
        assertEquals(titles.size(), authors.size());

        List<Integer> releaseYears = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                graphqlQueryRequest, "data.books[*].released.year",
                new TypeRef<List<Integer>>() {
                }
        );

        assertNotNull(releaseYears);
        assertEquals(titles.size(), releaseYears.size());
    }

    @Disabled("This test is disabled because it is not deterministic")
    @Test
    void testWithInput() {
        int expectedYearEqOrAfter = faker.number().numberBetween(2019, 2021);
        boolean expectedIsPrinted = faker.bool().bool();

        var releaseHistoryInput = ReleaseHistoryInput.newBuilder()
                .year(expectedYearEqOrAfter)
                .printedEdition(expectedIsPrinted)
                .build();
        var graphqlQuery = BooksByReleasedGraphQLQuery.newRequest()
                .releasedInput(releaseHistoryInput)
                .build();
        var projectionRoot = new BooksByReleasedProjectionRoot().books().title()
                .author().name()
                .originCountry()
                .getRoot().books().released().year().getRoot().totalCount();

        var graphqlQueryRequest = new GraphQLQueryRequest(
                graphqlQuery, projectionRoot
        ).serialize();
        logger.debug("graphqlQueryRequest: {}", graphqlQueryRequest);
        List<Integer> releaseYears = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQueryRequest, "data.booksByReleased[*].released.year"
        );

        assertNotNull(releaseYears);
        assertTrue(releaseYears.size() > 0);

        List<Boolean> releasePrintedEditions = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQueryRequest, "data.booksByReleased[*].released.printedEdition"
        );
        Set<Boolean> uniqueReleasePrintedEditions = new HashSet<>(releasePrintedEditions);

        assertNotNull(uniqueReleasePrintedEditions);
        assertTrue(uniqueReleasePrintedEditions.size() <= 1);

        if (!uniqueReleasePrintedEditions.isEmpty()) {
            assertTrue(uniqueReleasePrintedEditions.contains(expectedIsPrinted));
        }
    }
}
