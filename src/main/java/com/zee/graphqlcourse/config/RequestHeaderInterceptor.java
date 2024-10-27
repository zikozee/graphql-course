package com.zee.graphqlcourse.config;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 27 Oct, 2024
 */

@Component
public class RequestHeaderInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

        String header1 = request.getHeaders().getFirst("header1");
        String header2 = request.getHeaders().getFirst("header2");
        String header3 = request.getHeaders().getFirst("header3");
        String header4 = request.getHeaders().getFirst("header4");
        String header5 = request.getHeaders().getFirst("header5");
        String header6 = request.getHeaders().getFirst("header6");

        request.configureExecutionInput(((executionInput, builder) ->
                    builder.graphQLContext(
                            Map.of(
                                "header1", StringUtils.hasText(header1)? header1: "",
                                "header2", StringUtils.hasText(header2)? header2: "",
                                "header3", StringUtils.hasText(header3)? header3: "",
                                "header4", StringUtils.hasText(header4)? header4: "",
                                "header5", StringUtils.hasText(header5)? header5: "",
                                "header6", StringUtils.hasText(header6)? header6: ""
                            )
                    ).build()
                ));

        return chain.next(request);
    }
}
