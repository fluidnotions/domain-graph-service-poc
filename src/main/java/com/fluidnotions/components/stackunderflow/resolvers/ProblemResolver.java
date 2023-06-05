package com.fluidnotions.components.stackunderflow.resolvers;

import com.fluidnotions.databases.stackunderflow.entity.Problemz;
import com.fluidnotions.databases.stackunderflow.respoitory.ProblemzRepository;
import com.fluidnotions.databases.stackunderflow.util.GraphQLBeanMapping;
import com.fluidnotions.graphql.generated.DgsConstants;
import com.fluidnotions.graphql.generated.types.Problem;
import com.fluidnotions.graphql.generated.types.ProblemCreateInput;
import com.fluidnotions.graphql.generated.types.ProblemResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DgsComponent
public class ProblemResolver {
    private static final Logger logger = LoggerFactory.getLogger(ProblemResolver.class);

    @Autowired
    private ProblemzRepository problemzRepository;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getProblemLatestList() {
        var results = problemzRepository.findAll();
        return StreamSupport.stream(results.spliterator(), false).map(GraphQLBeanMapping::mapToGraphql).collect(Collectors.toList());
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemDetail(@InputArgument(name = "id") String problemId) {
        var problemz = problemzRepository.findById(UUID.fromString(problemId));
        return problemz.map(GraphQLBeanMapping::mapToGraphql).orElse(null);
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.ProblemCreate)
    public ProblemResponse createProblem(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "problem") ProblemCreateInput problemCreateInput) {

        Problemz entity = GraphQLBeanMapping.mapToEntity(problemCreateInput, null);
        var problemz = problemzRepository.save(entity);
        return new ProblemResponse(GraphQLBeanMapping.mapToGraphql(problemz));
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = DgsConstants.SUBSCRIPTION.ProblemAdded)
    public Flux<Problem> subscribeProblemAdded() {
        return null;
    }
}
