package com.zee.graphqlcourse.service;

import com.zee.graphqlcourse.codegen.types.CompanyDto;
import com.zee.graphqlcourse.codegen.types.EmployeeDto;
import com.zee.graphqlcourse.codegen.types.OutsourcedDto;
import com.zee.graphqlcourse.codegen.types.PersonAndEntitySearch;
import com.zee.graphqlcourse.entity.Company;
import com.zee.graphqlcourse.entity.Employee;
import com.zee.graphqlcourse.entity.Outsourced;
import com.zee.graphqlcourse.exception.NotFoundException;
import com.zee.graphqlcourse.repository.CompanyRepository;
import com.zee.graphqlcourse.repository.EmployeeRepository;
import com.zee.graphqlcourse.repository.OutsourcedRepository;
import com.zee.graphqlcourse.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 27 Oct, 2024
 */

@Service
@RequiredArgsConstructor
public class CommonService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final OutsourcedRepository outsourcedRepository;
    private final AddressService addressService;
    private final MapperUtil mapperUtil;


    public List<PersonAndEntitySearch> employeeWithCompanySearch(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(id);
        if(optionalEmployee.isEmpty()) throw new NotFoundException("Employee with id " + id + " not found");

        EmployeeDto employeeDto = optionalEmployee.map(mapperUtil::mapToEmployeeDto).get();
        employeeDto.setAddress(addressService.findAddressByEntityId(employeeDto.getEmployeeId()));

        Company company = companyRepository.findByName(employeeDto.getCompanyName())
                .orElseThrow(() -> new NotFoundException("Company with name " + employeeDto.getCompanyName() + " not found"));

        CompanyDto companyDto = mapperUtil.mapToCompanyDto(company);

        return List.of(employeeDto, companyDto);
    }

    public List<EmployeeDto> fetchEmployeesUsingHeaders(String header1, String header2, String header3) {
        return employeeRepository.findAllByEmployeeIdIn(List.of(header1, header2, header3))
                .stream()
                .map(employee -> {
                    EmployeeDto employeeDto = mapperUtil.mapToEmployeeDto(employee);
                    employeeDto.setAddress(addressService.findAddressByEntityId(employee.getEmployeeId()));
                    return employeeDto;
                })
                .toList();
    }

    public List<OutsourcedDto> fetchOutsourcedUsingHeaders(String header4, String header5) {
        return outsourcedRepository.findAllByOutsourceIdIn(List.of(header4, header5))
                .stream()
                .map(outsourced -> {
                    OutsourcedDto outsourcedDto = mapperUtil.mapToOutsourcedDto(outsourced);
                    outsourcedDto.setAddress(addressService.findAddressByEntityId(outsourcedDto.getOutsourceId()));
                    return outsourcedDto;
                })
                .toList();
    }


    public List<EmployeeDto> fetchEmployeesUsingHeadersAndArgument(String header6, List<String> staffIds) {
        staffIds.add(header6);
        return employeeRepository.findAllByEmployeeIdIn(staffIds)
                .stream()
                .map(employee -> {
                    EmployeeDto employeeDto = mapperUtil.mapToEmployeeDto(employee);
                    employeeDto.setAddress(addressService.findAddressByEntityId(employee.getEmployeeId()));
                    return employeeDto;
                })
                .toList();
    }
}
