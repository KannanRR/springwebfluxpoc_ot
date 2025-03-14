package com.example.springwebflux.CustomExceptions;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class GraphQLExceptionHandler implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment env) {
        if (exception instanceof ResourceNotFoundException) {
            return Mono.just(List.of(GraphQLError.newError()
                    .message(exception.getMessage())
                    .extensions(Map.of("errorCode", 404))
                    .errorType(ErrorType.NOT_FOUND)
                    .path(env.getExecutionStepInfo().getPath())
                    .build()));
        }

        // Default Internal Server Error
        return Mono.just(List.of(GraphQLError.newError()
                .message("Internal Server Error")
                .extensions(Map.of("errorCode", 500))
                .errorType(ErrorType.INTERNAL_ERROR)
                .path(env.getExecutionStepInfo().getPath())
                .build()));
    }
}
