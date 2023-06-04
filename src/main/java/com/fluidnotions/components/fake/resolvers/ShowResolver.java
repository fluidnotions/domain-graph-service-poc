package com.fluidnotions.components.fake.resolvers;


import com.fluidnotions.graphql.generated.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.stream.Collectors;

import static com.fluidnotions.databases.fake.FakeShowDataSource.SHOWS;

@DgsComponent
public class ShowResolver {

    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return SHOWS;
        }
        return SHOWS.stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }
}