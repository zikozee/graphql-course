package com.zee.graphqlcourse.service;

import com.zee.graphqlcourse.codegen.types.AllCompanyResponse;
import com.zee.graphqlcourse.codegen.types.CompanyDto;
import com.zee.graphqlcourse.codegen.types.CompanyInput;
import com.zee.graphqlcourse.codegen.types.CreationResponse;
import com.zee.graphqlcourse.entity.Company;
import com.zee.graphqlcourse.repository.CompanyRepository;
import com.zee.graphqlcourse.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 12 Oct, 2024
 */

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CreationResponse createCompany(CompanyInput input) {
        Company persistedCompany = companyRepository.save(mapperUtil.mapToCompanyEntity(input));

        return CreationResponse.newBuilder()
                .uuid(persistedCompany.getUuid().toString())
                .message("Company with name " + persistedCompany.getName() + " created successfully")
                .success(true)
                .build();
    }

    public AllCompanyResponse fetchAllCompany() {
        List<CompanyDto> companyDtos = companyRepository.findAll()
                .stream()
                .map(mapperUtil::mapToCompanyDto)
                .toList();

        return AllCompanyResponse.newBuilder()
                .message("All companies retrieved successfully")
                .success(true)
                .companies(companyDtos)
                .build();
    }
}
