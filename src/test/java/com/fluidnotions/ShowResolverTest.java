package com.fluidnotions;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ShowResolverTest {

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Test
    void testShows() {
        var query = """
                {
                  shows(titleFilter: "") {
                    releaseYear,
                    title
                  }
                }
                """;
        String title = dgsQueryExecutor.executeAndExtractJsonPath(query, "data.shows[0].title");
        Integer releaseYear = dgsQueryExecutor.executeAndExtractJsonPath(query, "data.shows[0].releaseYear");

        assertFalse(title.isEmpty());
        assertFalse(releaseYear == null);
    }
}
