package com.fluidnotions.resolvers;

import com.fluidnotions.databases.fake.FakeShowDataSource;
import com.fluidnotions.graphql.generated.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ShowMutation {
    private static final Logger logger = LoggerFactory.getLogger(ShowMutation.class);

    @DgsMutation
    public Integer addShow(@InputArgument String title, @InputArgument Integer releaseYear) {
        FakeShowDataSource.SHOWS.add(Show.newBuilder().title(title).releaseYear(releaseYear).build());
        return FakeShowDataSource.SHOWS.size();
    }

    @DgsMutation
    public Integer deleteShow(@InputArgument String title) {
        FakeShowDataSource.SHOWS.removeIf(s -> s.getTitle().equals(title));
        return FakeShowDataSource.SHOWS.size();
    }

    @DgsMutation
    public List<Show> updateShow(@InputArgument String title, @InputArgument String description) {
        Optional<Show> optionalShow = FakeShowDataSource.SHOWS.stream()
                .filter(s -> s.getTitle().equals(title))
                .findFirst();

        if (optionalShow.isPresent()) {
            Show show = optionalShow.get();
            show.setDescription(description);
        }

        return FakeShowDataSource.SHOWS;
    }

}
