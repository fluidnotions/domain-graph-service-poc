package com.fluidnotions.components.stackunderflow.resolvers;

import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.User;
import com.fluidnotions.graphql.generated.types.UserCreateInput;
import com.fluidnotions.graphql.generated.types.UserLoginInput;
import com.fluidnotions.graphql.generated.types.UserResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserResolver.class);

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Me)
    public User accountInfo(@RequestHeader(name = "authToken", required = true) String authToken) {
        return null;
    }


    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserCreate)
    public UserResponse createUser(@InputArgument(name = "user") UserCreateInput userCreateInput) {
        return null;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserLogin)
    public UserResponse userLogin(@InputArgument(name = "user") UserLoginInput userLoginInput) {
        return null;
    }


//    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserActivation)
//    public UserActivationResponse userActivation(
//        return null;
//    }


}
