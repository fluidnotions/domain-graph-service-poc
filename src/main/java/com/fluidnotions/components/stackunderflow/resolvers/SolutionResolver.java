package com.fluidnotions.components.stackunderflow.resolvers;

import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.Solution;
import com.fluidnotions.graphql.generated.types.SolutionCreateInput;
import com.fluidnotions.graphql.generated.types.SolutionResponse;
import com.fluidnotions.graphql.generated.types.SolutionVoteInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionResolver {
    private static final Logger logger = LoggerFactory.getLogger(SolutionResolver.class);

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.SolutionCreate)
    public SolutionResponse createSolution(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "solution") SolutionCreateInput solutionCreateInput
    ) {
        return null;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.SolutionVote)
    public SolutionResponse createSolutionVote(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput
    ) {
        return null;
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged)
    public Flux<Solution> subscribeSolutionVote(@InputArgument(name = "solutionId") String solutionId) {
        return null;
    }
}
