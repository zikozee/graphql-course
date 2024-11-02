package com.zee.graphqlcourse.search.outsourced;

import com.zee.graphqlcourse.codegen.types.Duty;
import com.zee.graphqlcourse.codegen.types.Gender;
import com.zee.graphqlcourse.entity.Outsourced;
import com.zee.graphqlcourse.search.SpecUtil;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 02 Nov, 2024
 */

public final class OutsourcedSearchSpecs {

    private OutsourcedSearchSpecs() {
        throw new RuntimeException("Cannot be instantiated");
    }

    public static Specification<Outsourced> uuidNotNull() {
        return (entity, cq, cb) -> cb.isNotNull(entity.get("uuid"));
    }

    public static Specification<Outsourced> nameContains(String nameKeyword) {

        // select * from outsourced o where o.name like '%Ezekiel%'
        return  (entity, cq, cb) -> cb.like(entity.get("name"), SpecUtil.contains(nameKeyword));
    }

    public static Specification<Outsourced> dobBetween(LocalDate dobStart, LocalDate dobEnd) {
        return (entity, cq, cb) -> cb.between(entity.get("dateOfBirth"), dobStart, dobEnd);
    }

    public static Specification<Outsourced> genderEquals(Gender gender) {
        return (entity, cq, cb) -> cb.equal(entity.get("gender"), gender);
    }

    public static Specification<Outsourced> salaryBetween(BigDecimal salaryFrom, BigDecimal salaryTo) {
        return (entity, cq, cb) -> cb.between(entity.get("salary"), salaryFrom, salaryTo);
    }

    public static Specification<Outsourced> ageBetween(Integer ageStart, Integer ageEnd) {
        return (entity, cq, cb) -> cb.between(entity.get("age"), ageStart, ageEnd);
    }

    public static Specification<Outsourced> dutyEquals(Duty duty) {
        return (entity, cq, cb) -> cb.equal(entity.get("duty"), duty);
    }
}
