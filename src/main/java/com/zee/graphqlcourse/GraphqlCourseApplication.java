package com.zee.graphqlcourse;

import com.zee.graphqlcourse.config.GraphqlRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

//@ImportRuntimeHints(GraphqlRegistrar.class)
@SpringBootApplication
public class GraphqlCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlCourseApplication.class, args);
    }

}
