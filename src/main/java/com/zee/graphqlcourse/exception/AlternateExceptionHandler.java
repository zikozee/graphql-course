package com.zee.graphqlcourse.exception;


import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 03 Nov, 2024
 */

//@Component
public class AlternateExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

        if(ex instanceof ProcessException){
            return GraphQLError.newError()
                    .message(ex.getLocalizedMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        if(ex instanceof NotFoundException){
            return GraphQLError.newError()
                    .message(ex.getLocalizedMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        return GraphQLError.newError()
                .message(ex.getLocalizedMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();

    }

    @Override // highest precedence
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        if(ex instanceof ProcessException){
            return List.of( GraphQLError.newError()
                    .message(ex.getLocalizedMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build());
        }

        if(ex instanceof NotFoundException){
            return List.of(GraphQLError.newError()
                    .message(ex.getLocalizedMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build());
        }

        return List.of(GraphQLError.newError()
                .message(ex.getLocalizedMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build());
    }
}
