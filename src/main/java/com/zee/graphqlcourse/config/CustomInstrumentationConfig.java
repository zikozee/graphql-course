package com.zee.graphqlcourse.config;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.Role;
import graphql.GraphQLError;
import graphql.execution.ResultPath;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.fieldvalidation.FieldAndArguments;
import graphql.execution.instrumentation.fieldvalidation.FieldValidationEnvironment;
import graphql.execution.instrumentation.fieldvalidation.FieldValidationInstrumentation;
import graphql.execution.instrumentation.fieldvalidation.SimpleFieldValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 09 Nov, 2024
 */

@Configuration
public class CustomInstrumentationConfig {

    @Bean
    Instrumentation paginationAgeInstrumentation() {
        ResultPath paginateEmployee = ResultPath.parse("/" + DgsConstants.QUERY.EmployeePagination);

        SimpleFieldValidation validation = new SimpleFieldValidation();
        validation.addRule(paginateEmployee, this.paginationAgeSearchGreaterThan17Rule());

        return new FieldValidationInstrumentation(validation);
    }


    @Bean
    Instrumentation retrieveAgeValidationInstrumentation() {
        ResultPath paginateEmployee = ResultPath.parse("/" + DgsConstants.MUTATION.CreateEmployee);

        SimpleFieldValidation validation = new SimpleFieldValidation();
        validation.addRule(paginateEmployee, this.age65In5YearsRule());

        return new FieldValidationInstrumentation(validation);
    }

    @Bean
    Instrumentation salaryBaseValidationInstrumentation() {
        ResultPath paginateEmployee = ResultPath.parse("/" + DgsConstants.MUTATION.CreateEmployee);

        SimpleFieldValidation validation = new SimpleFieldValidation();
        validation.addRule(paginateEmployee, this.salaryBelow50000ForInternRule());

        return new FieldValidationInstrumentation(validation);
    }


    private BiFunction<FieldAndArguments, FieldValidationEnvironment, Optional<GraphQLError>> paginationAgeSearchGreaterThan17Rule() {
        return ((fieldAndArguments, fieldValidationEnvironment) -> {
            Map<String, Object> argEmployee = fieldAndArguments.getArgumentValue(DgsConstants.QUERY.EMPLOYEEPAGINATION_INPUT_ARGUMENT.Search);

            LocalDate dobStart = (LocalDate) argEmployee.getOrDefault(DgsConstants.EMPLOYEESEARCHINPUT.DobStart, LocalDate.now());
            LocalDate dobEnd = (LocalDate) argEmployee.getOrDefault(DgsConstants.EMPLOYEESEARCHINPUT.DobEnd, LocalDate.now());

            int ageA = LocalDate.now().getYear() - dobStart.getYear();
            int ageB = LocalDate.now().getYear() - dobEnd.getYear();

            return ageA < 18 || ageB < 18
                    ? Optional.of(fieldValidationEnvironment.mkError("Age search for start and end must be greater or equal to 18"))
                    : Optional.empty();
        });
    }


    private BiFunction<FieldAndArguments, FieldValidationEnvironment, Optional<GraphQLError>> age65In5YearsRule() {
        return ((fieldAndArguments, fieldValidationEnvironment) -> {
            Map<String, Object> argEmployee = fieldAndArguments.getArgumentValue(DgsConstants.MUTATION.CREATEEMPLOYEE_INPUT_ARGUMENT.EmployeeOutsourced);

            int age = (Integer) argEmployee.getOrDefault(DgsConstants.EMPLOYEEOUTSOURCEDINPUT.Age, LocalDate.now());

            return age + 5 >=65
                    ? Optional.of(fieldValidationEnvironment.mkError("You must not be 65 years or more in 5 Years time"))
                    : Optional.empty();
        });
    }

    private BiFunction<FieldAndArguments, FieldValidationEnvironment, Optional<GraphQLError>> salaryBelow50000ForInternRule() {
        return ((fieldAndArguments, fieldValidationEnvironment) -> {
            Map<String, Object> argEmployee = fieldAndArguments.getArgumentValue(DgsConstants.MUTATION.CREATEEMPLOYEE_INPUT_ARGUMENT.EmployeeOutsourced);

            BigDecimal salary = (BigDecimal) argEmployee.getOrDefault(DgsConstants.EMPLOYEEOUTSOURCEDINPUT.Salary, BigDecimal.ZERO);
            String role = (String) argEmployee.get(DgsConstants.EMPLOYEEOUTSOURCEDINPUT.Role);


            return (salary.compareTo(BigDecimal.valueOf(49999L)) > 0  && role.equalsIgnoreCase(Role.INTERN.name()))
                    ? Optional.of(fieldValidationEnvironment.mkError("You can't earn this much as an intern"))
                    : Optional.empty();
        });
    }
}
