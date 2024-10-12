package com.zee.graphqlcourse.service;

import com.zee.graphqlcourse.codegen.types.*;
import com.zee.graphqlcourse.entity.Address;
import com.zee.graphqlcourse.entity.Employee;
import com.zee.graphqlcourse.entity.Outsourced;
import com.zee.graphqlcourse.repository.EmployeeRepository;
import com.zee.graphqlcourse.repository.OutsourcedRepository;
import com.zee.graphqlcourse.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 12 Oct, 2024
 */

@Service
@RequiredArgsConstructor
public class EmployeeOutsourcedService {
    private final EmployeeRepository employeeRepository;
    private final OutsourcedRepository outsourcedRepository;
    private final MapperUtil mapperUtil;
    private final AddressService addressService;

    public CreationResponse createEmployeeOutsourced(EmployeeOutsourcedInput input){
        return (!StringUtils.hasText(input.getOutsourceId()) && input.getDuty() == null)
                ? createEmployee(input) : createOutsourced(input);
    }

    private CreationResponse createEmployee(EmployeeOutsourcedInput input) {
        if(!StringUtils.hasText(input.getEmployeeId()) || !StringUtils.hasText(input.getDepartmentNo())
        || !StringUtils.hasText(input.getEmail()) || input.getRole() == null) {
            throw new RuntimeException("employeeId, departmentNo, email, role is required");
        }


        EmployeeDto employeeDto = mapperUtil.mapToEmployeeDto(input);
        Employee persistedEmployee = employeeRepository.save(mapperUtil.mapToEmployeeEntity(employeeDto));

        List<Address> addressList = input.getAddress().stream()
                .map(addressInput -> {
                    Address address = mapperUtil.mapToAddressEntity(addressInput);
                    address.setEntityId(persistedEmployee.getEmployeeId());
                    return address;
                })
                .toList();

        addressService.saveAll(addressList);

        return CreationResponse.newBuilder()
                .uuid(persistedEmployee.getUuid().toString())
                .message("Employee with name " + persistedEmployee.getName() + " created successfully")
                .success(true)
                .build();
    }


    private CreationResponse createOutsourced(EmployeeOutsourcedInput input) {

        OutsourcedDto outsourcedDto = mapperUtil.mapToOutsourcedDto(input);
        Outsourced persistedOutsourced = outsourcedRepository.save(mapperUtil.mapToOutsourcedEntity(outsourcedDto));

        List<Address> addressList = input.getAddress().stream()
                .map(addressInput -> {
                    Address address = mapperUtil.mapToAddressEntity(addressInput);
                    address.setEntityId(persistedOutsourced.getOutsourceId());
                    return address;
                })
                .toList();

        addressService.saveAll(addressList);

        return CreationResponse.newBuilder()
                .uuid(persistedOutsourced.getUuid().toString())
                .message("Outsourced with name " + persistedOutsourced.getName() + " created successfully")
                .success(true)
                .build();
    }


    public CreationResponse updateExistingEmployeeAddress(AddressInput input){

        AddressDto foundAddress = addressService
                .findAddressByEntityIdAndUuid(input.getEntityId(), UUID.fromString(input.getUuid()))
                .orElseThrow(() -> new RuntimeException("address for employee with id '" + input.getEntityId() + "' not found" ));


        if(input.getEntityId().trim().equalsIgnoreCase(foundAddress.getEntityId())
                && input.getUuid().trim().equalsIgnoreCase(foundAddress.getUuid())
                && input.getStreet().trim().equalsIgnoreCase(foundAddress.getStreet())
                && input.getCity().trim().equalsIgnoreCase(foundAddress.getCity())
                && input.getState().trim().equalsIgnoreCase(foundAddress.getState())
                && input.getZipCode() == foundAddress.getZipCode()){
            throw new RuntimeException("address for employee with id: '" + input.getEntityId() +"'already exist");
        }

        Address newAddress = mapperUtil.mapToAddressEntity(input);
        newAddress.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Address persistedAddress= addressService.save(newAddress);

        return CreationResponse.newBuilder()
                .uuid(persistedAddress.getUuid().toString())
                .message("Employee Address updated successfully")
                .success(true)
                .build();
    }

    public CreationResponse updateEmployeeDetails(EmployeeUpdateInput input){

        Employee employee = employeeRepository.findByEmployeeId(input.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee with id '" + input.getEmployeeId() + "' not found"));



        employee.setSalary(input.getSalary());
        employee.setAge(input.getAge());
        employee.setRole(input.getRole());
        employee.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Employee updatedEmployee = employeeRepository.save(employee);

        return CreationResponse.newBuilder()
                .uuid(updatedEmployee.getUuid().toString())
                .message("Employee details updated successfully")
                .success(true)
                .build();
    }

}
