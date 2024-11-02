package com.zee.graphqlcourse.search.employee;

import com.zee.graphqlcourse.codegen.types.Gender;
import com.zee.graphqlcourse.codegen.types.Role;
import com.zee.graphqlcourse.entity.Employee;
import com.zee.graphqlcourse.search.SpecUtil;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 02 Nov, 2024
 */


public final class EmployeeSearchSpecs {

    private EmployeeSearchSpecs() {}

    public static Specification<Employee> uuidNotNull(){
        return (entity, cq, cb) -> cb.isNotNull(entity.get("uuid"));
    }

    public static Specification<Employee> nameContains(String nameKeyword){
        return (entity, cq, cb) -> cb.like(cb.lower(entity.get("name")), SpecUtil.contains(nameKeyword));
    }

    public static Specification<Employee> dobBetween(LocalDate dobStart, LocalDate dobEnd){
        return (entity, cq, cb) -> cb.between(entity.get("dateOfBirth"), dobStart, dobEnd);
    }

    public static Specification<Employee> genderEquals(Gender gender){
        return (entity, cq, cb) -> cb.equal(entity.get("gender"), gender);
    }

    public static Specification<Employee> salaryBetween(BigDecimal salaryFrom, BigDecimal salaryTo){
        return (entity, cq, cb) -> cb.between(entity.get("salary"), salaryFrom, salaryTo);
    }

    public static Specification<Employee> ageBetween(int ageFrom, int ageTo){
        return (entity, cq, cb) -> cb.between(entity.get("age"), ageFrom, ageTo);
    }

    public static Specification<Employee> roleEquals(Role role){
        return (entity, cq, cb) -> cb.equal(entity.get("role"), role);
    }
}
