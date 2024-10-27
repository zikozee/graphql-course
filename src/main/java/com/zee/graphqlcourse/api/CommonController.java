package com.zee.graphqlcourse.api;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.EmployeeDto;
import com.zee.graphqlcourse.codegen.types.OutsourcedDto;
import com.zee.graphqlcourse.codegen.types.PersonAndEntitySearch;
import com.zee.graphqlcourse.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 27 Oct, 2024
 */

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final CommonService commonService;


    @SchemaMapping(
            typeName = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.EmployeeWithCompanySearch
    )
    public List<PersonAndEntitySearch> employeeWithCompanySearch(@Argument("employeeId")String id){
        return commonService.employeeWithCompanySearch(id);
    }

    @SchemaMapping(
            typeName = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.FetchEmployeesUsingHeaders
    )
    public List<EmployeeDto> fetchEmployeesUsingHeaders(
            @ContextValue(name = "header1", required = false) String header1,
            @ContextValue(value = "header2") String header2,
            @ContextValue(name = "header3", required = false) String header3
    ){

        return commonService.fetchEmployeesUsingHeaders(header1, header2, header3);
    }

    @SchemaMapping(
            typeName = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.FetchOutsourcedUsingHeaders
    )
    public List<OutsourcedDto> fetchOutsourcedUsingHeaders(
            @ContextValue(name = "header4", required = false) String header4,
            @ContextValue(value = "header5") String header5
    ){

        return commonService.fetchOutsourcedUsingHeaders(header4, header5);
    }

    @SchemaMapping(
            typeName = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.FetchEmployeesUsingHeadersAndArgument
    )
    public List<EmployeeDto> fetchEmployeesUsingHeadersAndArgument(
            @ContextValue(name="header6", required = false) String header6,
            @Argument List<String> staffIds
    ){

        // note you can use in combination with any query or mutation
        return commonService.fetchEmployeesUsingHeadersAndArgument(header6, staffIds);
    }

}
