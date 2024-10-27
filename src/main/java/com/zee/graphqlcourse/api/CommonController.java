package com.zee.graphqlcourse.api;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.PersonAndEntitySearch;
import com.zee.graphqlcourse.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
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
            field = "employeeWithCompanySearch"
    )
    public List<PersonAndEntitySearch> employeeWithCompanySearch(@Argument("employeeId")String id){
        return commonService.employeeWithCompanySearch(id);
    }
}
