package com.fluidnotions.components.stackunderflow.resolvers;

import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.SearchInput;
import com.fluidnotions.graphql.generated.types.SearchableItem;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@DgsComponent
public class ItemSearchResolver {
    private static final Logger logger = LoggerFactory.getLogger(ItemSearchResolver.class);

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ItemSearch)
    public List<SearchableItem> searchItems(
            @InputArgument(name = "filter") SearchInput filter) {
        return null;
    }
}
