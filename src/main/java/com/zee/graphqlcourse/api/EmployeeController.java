package com.zee.graphqlcourse.api;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.*;

import com.zee.graphqlcourse.service.EmployeeOutsourcedService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Oct, 2024
 */

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeOutsourcedService service;

    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.CreateEmployee
    )
    public CreationResponse createEmployee(@Argument(value = "employeeOutsourced") EmployeeOutsourcedInput input) {
        return service.createEmployeeOutsourced(input);
    }

    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UpdateExistingEmployeeAddress
    )
    public CreationResponse updateExistingEmployeeAddress(@Argument(value = "addressInput") AddressInput input) {
        return service.updateExistingEmployeeAddress(input);
    }

    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UpdateEmployeeDetails
    )
    public CreationResponse updateEmployeeDetails(@Argument(value = "employeeUpdate") EmployeeUpdateInput input) {
        return service.updateEmployeeDetails(input);
    }


    @SchemaMapping(
            typeName = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.EmployeeSearch
    )
    public List<Person> employeeSearch(@Argument("outsourced") Boolean outsourced) {
        return service.employeeSearch(outsourced);
    }

    @SchemaMapping(
            typeName = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.EmployeeSearchByStaffId
    )
    public Person employeeSearchByStaffId(@Argument("staffId") String id) {
        return service.employeeSearchByStaffId(id);
    }


    @SchemaMapping(
            typeName = DgsConstants.SUBSCRIPTION_TYPE,
            field = DgsConstants.SUBSCRIPTION.CreateEmployee
    )
    public Flux<EmployeeDto> createEmployee(){
        return service.employeeDtoFlux();
    }















}
