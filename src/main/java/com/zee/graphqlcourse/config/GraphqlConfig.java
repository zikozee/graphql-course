package com.zee.graphqlcourse.config;

import graphql.scalars.ExtendedScalars;
import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 03 Oct, 2024
 */

@Configuration
public class GraphqlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {

        ValidationSchemaWiring schemaWiring = new ValidationSchemaWiring(
                ValidationRules.newValidationRules()
                        .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
                        .build()
        );

        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.NegativeInt)
                .scalar(ExtendedScalars.NonNegativeInt)
                .scalar(ExtendedScalars.NonNegativeFloat)
                .scalar(ExtendedScalars.PositiveInt)
                .scalar(ExtendedScalars.PositiveFloat)
                .scalar(ExtendedScalars.NonPositiveFloat)
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.Currency)

                .directiveWiring(schemaWiring)

                .build();
    }
}
