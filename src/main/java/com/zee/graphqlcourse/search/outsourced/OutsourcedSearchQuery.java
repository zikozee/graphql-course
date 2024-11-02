package com.zee.graphqlcourse.search.outsourced;

import com.zee.graphqlcourse.codegen.types.OutsourcedSearchInput;
import com.zee.graphqlcourse.entity.Outsourced;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 02 Nov, 2024
 */

@Component
public class OutsourcedSearchQuery {

    public static final String OUTSOURCED_UUID = "uuid";

    public Specification<Outsourced> buildOutsourcedSearchParam(OutsourcedSearchInput input) {
        Specification<Outsourced> specs = OutsourcedSearchSpecs.uuidNotNull();

        if(StringUtils.hasText(input.getName())){
            specs = specs.and(OutsourcedSearchSpecs.nameContains(input.getName()));
        }

        if(input.getDobStart() != null && input.getDobEnd() != null){
            if(input.getDobStart().isBefore(input.getDobEnd())){
                specs = specs.and(OutsourcedSearchSpecs.dobBetween(input.getDobStart(), input.getDobEnd()));
            }else {
                specs = specs.and(OutsourcedSearchSpecs.dobBetween(input.getDobStart(), LocalDate.now()));
            }
        }

        if(input.getGender() != null){
            specs = specs.and(OutsourcedSearchSpecs.genderEquals(input.getGender()));
        }

        if(input.getSalaryTo() != null && input.getSalaryFrom() != null){
            specs = specs.and(OutsourcedSearchSpecs.salaryBetween(input.getSalaryFrom(), input.getSalaryTo()));
        }

        if(input.getAgeStart() != null && input.getAgeEnd() != null){
            specs = specs.and(OutsourcedSearchSpecs.ageBetween(input.getAgeStart(), input.getAgeEnd()));
        }

        if(input.getDuty() != null){
            specs = specs.and(OutsourcedSearchSpecs.dutyEquals(input.getDuty()));
        }

        return specs;
    }
}
