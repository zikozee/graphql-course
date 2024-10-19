package com.zee.graphqlcourse.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Configuration
public class ProjectConfig {

    @Bean
    Faker faker(){
        return new Faker();
    }
}
