package com.zee.graphqlcourse.search.employee;

import com.zee.graphqlcourse.codegen.types.EmployeeSearchInput;
import com.zee.graphqlcourse.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 02 Nov, 2024
 */

@Component
public class EmployeeSearchQuery {
    public static final String EMPLOYEE_UUID = "uuid";

    public Specification<Employee> buildEmployeeSearchParams(EmployeeSearchInput input) {

        Specification<Employee> specs = EmployeeSearchSpecs.uuidNotNull();

        if(StringUtils.hasText(input.getName())){
            specs = specs.and(EmployeeSearchSpecs.nameContains(input.getName()));
        }

        if (input.getDobStart() != null && input.getDobEnd() != null) {
            if(input.getDobStart().isBefore(input.getDobEnd())){
                specs = specs.and(EmployeeSearchSpecs.dobBetween(input.getDobStart(), input.getDobEnd()));
            }else {
                specs = specs.and(EmployeeSearchSpecs.dobBetween(input.getDobStart(), LocalDate.now()));
            }
        }

        if(input.getGender() != null){
            specs = specs.and(EmployeeSearchSpecs.genderEquals(input.getGender()));
        }

        if(input.getSalaryTo() != null  && input.getSalaryFrom() != null){
            specs = specs.and(EmployeeSearchSpecs.salaryBetween(input.getSalaryFrom(), input.getSalaryTo()));
        }

        if(input.getAgeStart() != null && input.getAgeEnd() != null){
            if(input.getAgeStart() <= input.getAgeEnd())
                specs = specs.and(EmployeeSearchSpecs.ageBetween(input.getAgeStart(), input.getAgeEnd()));
        }

        if(input.getRole() != null){
            specs = specs.and(EmployeeSearchSpecs.roleEquals(input.getRole()));
        }

        return specs;
    }
}
