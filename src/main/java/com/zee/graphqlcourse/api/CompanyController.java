package com.zee.graphqlcourse.api;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Oct, 2024
 */

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.CompanyInput;
import com.zee.graphqlcourse.codegen.types.CreationResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CompanyController {


    @SchemaMapping(
            typeName = DgsConstants.MUTATION.TYPE_NAME,
            field = DgsConstants.MUTATION.CreateCompany
    )
//    @MutationMapping(value = DgsConstants.MUTATION.CreateCompany)
    public CreationResponse createCompany(@Argument(value = "companyInput") CompanyInput input) {
        return null;
    }


}
