package com.zee.graphqlcourse.service;

import com.zee.graphqlcourse.codegen.types.*;
import com.zee.graphqlcourse.entity.Address;
import com.zee.graphqlcourse.entity.Department;
import com.zee.graphqlcourse.repository.DepartmentRepository;
import com.zee.graphqlcourse.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 12 Oct, 2024
 */

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final MapperUtil mapperUtil;
    private final AddressService addressService;

    public CreationResponse createDepartment(DepartmentInput input) {

        Department persistedDept = departmentRepository.save(mapperUtil.mapToDepartmentEntity(input));

//        input.getAddress().setEntityId(persistedDept.getDepartmentNo());
        Address address = mapperUtil.mapToAddressEntity(input.getAddress());
        address.setEntityId(persistedDept.getDepartmentNo());

        addressService.save(address);

        return CreationResponse.newBuilder()
                .uuid(persistedDept.getUuid().toString())
                .message("Department with name " + persistedDept.getName() + " created successfully")
                .success(true)
                .build();

    }

    public DepartmentsResponse fetchAllDepartmentsByCompanyName(String companyName) {
        List<DepartmentDto> departmentDtos = departmentRepository.findDepartmentByCompanyName(companyName)
                .stream()
                .map(mapperUtil::mapToDepartmentDto)
                .peek(departmentDto -> {
                    List<AddressDto> addressDtos = addressService.findAddressByEntityId(departmentDto.getDepartmentNo());
                    departmentDto.setAddress(addressDtos.getFirst());
                })
                .toList();

        if(departmentDtos.isEmpty()) {
            return DepartmentsResponse.newBuilder()
                    .message("No departments found with companyName " + companyName)
                    .success(false)
                    .build();
        }

        return DepartmentsResponse.newBuilder()
                .success(true)
                .message("Departments under '" + companyName + "' retrieved successfully")
                .departments(departmentDtos)
                .build();
    }
}
