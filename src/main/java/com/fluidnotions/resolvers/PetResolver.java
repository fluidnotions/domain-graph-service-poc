package com.fluidnotions.resolvers;

import com.fluidnotions.databases.fake.FakePetDataSource;
import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.Cat;
import com.fluidnotions.graphql.generated.types.Dog;
import com.fluidnotions.graphql.generated.types.Pet;
import com.fluidnotions.graphql.generated.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//FIXME: cleanup code and remove the need for org.apache.commons:commons-lang3:3.12.0 + additional filter added needs to be fixed
@DgsComponent
public class PetResolver {
    private static final Logger logger = LoggerFactory.getLogger(PetResolver.class);

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Pets)
    public List<Pet> getPets(@InputArgument(name = "petFilter")
                             Optional<PetFilter> filter) {
        if (filter.isEmpty()) {
            return FakePetDataSource.PET_LIST;
        }

        return FakePetDataSource.PET_LIST.stream().filter(
                pet -> this.matchFilter(filter.get(), pet)
        ).collect(Collectors.toList());
    }

    private boolean matchFilter(PetFilter petFilter, Pet pet) {
        if (petFilter.getPetType() != null) {
            if (petFilter.getPetType().equalsIgnoreCase("Dog")) {
                return pet instanceof Dog;
            } else if (petFilter.getPetType().equalsIgnoreCase("Cat")) {
                return pet instanceof Cat;
            }
        } else if (StringUtils.isBlank(petFilter.getPetType())) {
            return true;
        }

        if (petFilter.getFoodType() != null) {
            return petFilter.getFoodType().equals(pet.getFood());
        } else {
            return true;
        }
    }

}
