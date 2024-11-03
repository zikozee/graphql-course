package com.zee.graphqlcourse.exception;


import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 03 Nov, 2024
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @GraphQlExceptionHandler(ProcessException.class)
    public GraphQLError exception(ProcessException ex, final DataFetchingEnvironment env) {
        return GraphQLError.newError()
                .message(ex.getLocalizedMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    @GraphQlExceptionHandler(NotFoundException.class)
    public GraphQLError exception(NotFoundException ex, final DataFetchingEnvironment env) {
        return GraphQLError.newError()
                .message(ex.getLocalizedMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    @GraphQlExceptionHandler(RuntimeException.class)
    public GraphQLError exception(RuntimeException ex, final DataFetchingEnvironment env) {
        return GraphQLError.newError()
                .message(ex.getLocalizedMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    @GraphQlExceptionHandler(Exception.class)
    public GraphQLError exception(Exception ex, final DataFetchingEnvironment env) {
        return GraphQLError.newError()
                .message(ex.getLocalizedMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
