package com.fluidnotions.components.fake.resolvers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluidnotions.graphql.generated.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

record HeaderEcho(String optionalHeader, String mandatoryHeader, String optionalParam, String mandatoryParam) {
    @Override
    public String toString() {
        return """
                 optionalHeader: %s,
                 mandatoryHeader: %s,
                 optionalParam: %s,
                 mandatoryParam: %s
                """.formatted(optionalHeader, mandatoryHeader, optionalParam, mandatoryParam);
    }
}


@DgsComponent
public class HeaderEchoResolver {
    private static final Logger logger = LoggerFactory.getLogger(HeaderEchoResolver.class);

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.HeaderEcho)
    public HeaderEcho headerEcho(
            @RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
            @RequestHeader(name = "mandatoryHeader", required = true) String mandatoryHeader,
            @RequestParam(name = "optionalParam", required = false) String optionalParam,
            @RequestParam(name = "mandatoryParam", required = true) String mandatoryParam
    ) {
        var response = new HeaderEcho(optionalHeader, mandatoryHeader, optionalParam, mandatoryParam);
        return response;
    }

    private String asJson(HeaderEcho response) {
        try {
            var jsonStr = new ObjectMapper().writeValueAsString(response);
            return jsonStr;
        } catch (Exception e) {
            logger.error("Error while converting to json", e);
            return response.toString();
        }
    }
}
