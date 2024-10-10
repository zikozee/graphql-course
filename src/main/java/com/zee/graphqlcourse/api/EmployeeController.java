package com.zee.graphqlcourse.api;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.AddressInput;
import com.zee.graphqlcourse.codegen.types.CreationResponse;
import com.zee.graphqlcourse.codegen.types.EmployeeOutsourcedInput;

import com.zee.graphqlcourse.codegen.types.EmployeeUpdateInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Oct, 2024
 */

@Controller
public class EmployeeController {

    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.CreateEmployee
    )
    public CreationResponse createEmployee(@Argument(value = "employeeOutsourced") EmployeeOutsourcedInput input) {
        return null;
    }

    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UpdateExistingEmployeeAddress
    )
    public CreationResponse updateExistingEmployeeAddress(@Argument(value = "addressInput") AddressInput input) {
        return null;
    }

    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.UpdateEmployeeDetails
    )
    public CreationResponse updateEmployeeDetails(@Argument(value = "employeeUpdate") EmployeeUpdateInput input) {
        return null;
    }

}
