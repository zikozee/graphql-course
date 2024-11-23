package com.zee.graphqlcourse.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 23 Nov, 2024
 */

@Configuration
public class ClientConfig {

    @Bean
    HttpGraphQlTester httpGraphQlTester() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080/graphql")
                .defaultHeader(HttpHeaders.AUTHORIZATION, getAccessTokenResponse().tokenType() + " " + getAccessTokenResponse().accessToken)
                .build();

        return HttpGraphQlTester.create(client);
    }

    public AccessTokenResponse getAccessTokenResponse() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("scope", "openid");

        RestClient restClient = RestClient.create();

        return restClient
                .post().uri("http://localhost:8099/oauth2/token")
                .body(formData)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(("client" + ":" + "secret").getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .body(AccessTokenResponse.class);

    }

    private record AccessTokenResponse(
            @JsonProperty("access_token")
            String accessToken,
            @JsonProperty("token_type")
            String tokenType
    ){}
}
