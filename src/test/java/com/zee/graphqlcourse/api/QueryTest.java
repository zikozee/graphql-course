package com.zee.graphqlcourse.api;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.EmployeePagination;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Nov, 2024
 */

@Slf4j
@SpringBootTest
@AutoConfigureGraphQlTester
class QueryTest {

    // use this strictly before security was introduced
//    @Autowired
//    GraphQlTester httpGraphQlTester;

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @DisplayName("fetch Employee and Company ")
    @Test
    void employeeWithCompanySearchTest(){

        Map<String, Object> variable = new HashMap<>();

        variable.put("dobStart", "1960-09-04");
        variable.put("dobEnd", "1998-09-04");
        variable.put("gender", "MALE");
        variable.put("salaryFrom", "30000.05");
        variable.put("salaryTo", "50000.52");
        variable.put("ageStart",  23);
        variable.put("ageEnd" , 30);
        variable.put("role", "INTERN");


        GraphQlTester.Entity<EmployeePagination, ?> entity = this.httpGraphQlTester
                .documentName("paginationTest")
                .variable("input", variable)
                .variable("page", 1)
                .variable("size", 5)
                .execute()
                .path(DgsConstants.QUERY.EmployeePagination)
                .entity(EmployeePagination.class);

        EmployeePagination pagination = entity.get();
        log.info("EmployeeDto : {}", pagination);

        Assertions.assertThat(pagination).isNotNull();
        Assertions.assertThat(pagination.getPage()).isEqualTo(1);
        Assertions.assertThat(pagination.getSize()).isEqualTo(5);
    }
}
