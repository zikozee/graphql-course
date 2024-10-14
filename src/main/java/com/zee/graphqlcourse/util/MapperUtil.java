package com.zee.graphqlcourse.util;

import com.zee.graphqlcourse.codegen.types.*;
import com.zee.graphqlcourse.entity.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 12 Oct, 2024
 */

@Component
public class MapperUtil {

    //Jmapper, modelMapper, Mapstruct, DozerMapper, BeanCopyUtils

    public Company mapToCompanyEntity(final CompanyInput input) {
        Company company = new Company();

        company.setName(input.getName());
        company.setRcNumber(input.getRcNumber());
        company.setHeadOffice(input.getHeadOffice());
        company.setCountry(input.getCountry());
        company.setBusinessType(input.getBusinessType());

        return company;
    }

    public CompanyDto mapToCompanyDto(final Company company) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setUuid(company.getUuid().toString());
        companyDto.setName(company.getName());
        companyDto.setRcNumber(company.getRcNumber());
        companyDto.setHeadOffice(company.getHeadOffice());
        companyDto.setCountry(company.getCountry());
        companyDto.setBusinessType(company.getBusinessType());

        return companyDto;
    }

    public Department mapToDepartmentEntity(final DepartmentInput input) {

        Department department = new Department();

        department.setName(input.getName());
        department.setDepartmentNo(input.getDepartmentNo());
        department.setCompanyName(input.getCompanyName());
        department.setRcNumber(input.getRcNumber());
        department.setDivision(input.getDivision());

        return department;
    }

    public DepartmentDto mapToDepartmentDto(final Department department) {
        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setUuid(department.getUuid().toString());
        departmentDto.setName(department.getName());
        departmentDto.setDepartmentNo(department.getDepartmentNo());
        departmentDto.setCompanyName(department.getCompanyName());
        departmentDto.setRcNumber(department.getRcNumber());
        departmentDto.setDivision(department.getDivision());

        return departmentDto;
    }

    public Address mapToAddressEntity(final AddressInput input) {
        Address address = new Address();

        address.setUuid(UUID.fromString(input.getUuid()));
        address.setEntityId(input.getEntityId());
        address.setStreet(input.getStreet());
        address.setCity(input.getCity());
        address.setState(input.getState());
        address.setZipCode(input.getZipCode());

        return address;
    }

    public AddressDto mapToAddressDto(final Address address) {
        AddressDto addressDto = new AddressDto();

        addressDto.setUuid(address.getUuid().toString());
        addressDto.setEntityId(address.getEntityId());
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setZipCode(address.getZipCode());

        return addressDto;
    }

    public EmployeeDto mapToEmployeeDto(final EmployeeOutsourcedInput employeeOutsourcedInput) {

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setName(employeeOutsourcedInput.getName());
        employeeDto.setDateOfBirth(employeeOutsourcedInput.getDateOfBirth());
        employeeDto.setGender(employeeOutsourcedInput.getGender());
        employeeDto.setSalary(employeeOutsourcedInput.getSalary());
        employeeDto.setAge(employeeOutsourcedInput.getAge());
        employeeDto.setPhone(employeeOutsourcedInput.getPhone());
        employeeDto.setCompanyName(employeeOutsourcedInput.getCompanyName());

        employeeDto.setEmployeeId(employeeOutsourcedInput.getEmployeeId());
        employeeDto.setDepartmentNo(employeeOutsourcedInput.getDepartmentNo());
        employeeDto.setEmail(employeeOutsourcedInput.getEmail());
        employeeDto.setRole(employeeOutsourcedInput.getRole());

        return employeeDto;
    }

    public OutsourcedDto mapToOutsourcedDto(final EmployeeOutsourcedInput employeeOutsourcedInput) {

        OutsourcedDto outsourcedDto = new OutsourcedDto();

        outsourcedDto.setName(employeeOutsourcedInput.getName());
        outsourcedDto.setDateOfBirth(employeeOutsourcedInput.getDateOfBirth());
        outsourcedDto.setGender(employeeOutsourcedInput.getGender());
        outsourcedDto.setSalary(employeeOutsourcedInput.getSalary());
        outsourcedDto.setAge(employeeOutsourcedInput.getAge());
        outsourcedDto.setPhone(employeeOutsourcedInput.getPhone());
        outsourcedDto.setCompanyName(employeeOutsourcedInput.getCompanyName());

        outsourcedDto.setOutsourceId(employeeOutsourcedInput.getOutsourceId());
        outsourcedDto.setDuty(employeeOutsourcedInput.getDuty());

        return outsourcedDto;
    }


    public Employee mapToEmployeeEntity(final EmployeeDto employeeDto) {
        Employee employee = new Employee();

        employee.setName(employeeDto.getName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setGender(employeeDto.getGender());
        employee.setSalary(employeeDto.getSalary());
        employee.setAge(employeeDto.getAge());
        employee.setPhone(employeeDto.getPhone());
        employee.setCompanyName(employeeDto.getCompanyName());

        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setDepartmentNo(employeeDto.getDepartmentNo());
        employee.setEmail(employeeDto.getEmail());
        employee.setRole(employeeDto.getRole());

        return employee;
    }

    public EmployeeDto mapToEmployeeDto(final Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setUuid(employee.getUuid().toString());
        employeeDto.setName(employee.getName());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setGender(employee.getGender());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setAge(employee.getAge());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setCompanyName(employee.getCompanyName());

        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setDepartmentNo(employee.getDepartmentNo());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setRole(employee.getRole());

        return employeeDto;
    }

    public Outsourced mapToOutsourcedEntity(final OutsourcedDto outsourcedDto) {
        Outsourced outsourced = new Outsourced();

        outsourced.setName(outsourcedDto.getName());
        outsourced.setDateOfBirth(outsourcedDto.getDateOfBirth());
        outsourced.setGender(outsourcedDto.getGender());
        outsourced.setSalary(outsourcedDto.getSalary());
        outsourced.setAge(outsourcedDto.getAge());
        outsourced.setPhone(outsourcedDto.getPhone());
        outsourced.setCompanyName(outsourcedDto.getCompanyName());

        outsourced.setOutsourceId(outsourcedDto.getOutsourceId());
        outsourced.setDuty(outsourcedDto.getDuty());

        return outsourced;
    }

    public OutsourcedDto mapToOutsourcedDto(final Outsourced outsourced) {
        OutsourcedDto outsourcedDto = new OutsourcedDto();

        outsourcedDto.setUuid(outsourced.getUuid().toString());
        outsourcedDto.setName(outsourced.getName());
        outsourcedDto.setDateOfBirth(outsourced.getDateOfBirth());
        outsourcedDto.setGender(outsourced.getGender());
        outsourcedDto.setSalary(outsourced.getSalary());
        outsourcedDto.setAge(outsourced.getAge());
        outsourcedDto.setPhone(outsourced.getPhone());
        outsourcedDto.setCompanyName(outsourced.getCompanyName());

        outsourcedDto.setOutsourceId(outsourced.getOutsourceId());
        outsourcedDto.setDuty(outsourced.getDuty());

        return outsourcedDto;
    }



}
