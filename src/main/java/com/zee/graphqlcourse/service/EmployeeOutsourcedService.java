package com.zee.graphqlcourse.service;

import com.zee.graphqlcourse.codegen.types.*;
import com.zee.graphqlcourse.entity.Address;
import com.zee.graphqlcourse.entity.Employee;
import com.zee.graphqlcourse.entity.Outsourced;
import com.zee.graphqlcourse.exception.NotFoundException;
import com.zee.graphqlcourse.exception.ProcessException;
import com.zee.graphqlcourse.repository.EmployeeRepository;
import com.zee.graphqlcourse.repository.OutsourcedRepository;
import com.zee.graphqlcourse.search.SpecUtil;
import com.zee.graphqlcourse.search.employee.EmployeeSearchQuery;
import com.zee.graphqlcourse.search.outsourced.OutsourcedSearchQuery;
import com.zee.graphqlcourse.util.MapperUtil;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.zee.graphqlcourse.search.employee.EmployeeSearchQuery.EMPLOYEE_UUID;
import static com.zee.graphqlcourse.search.outsourced.OutsourcedSearchQuery.OUTSOURCED_UUID;

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
    private final OutsourcedSearchQuery outsourcedSearchQuery;
    private final EmployeeSearchQuery employeeSearchQuery;

    public CreationResponse createEmployeeOutsourced(EmployeeOutsourcedInput input){
        return (!StringUtils.hasText(input.getOutsourceId()) && input.getDuty() == null)
                ? createEmployee(input) : createOutsourced(input);
    }

    private CreationResponse createEmployee(EmployeeOutsourcedInput input) {
        if(!StringUtils.hasText(input.getEmployeeId()) || !StringUtils.hasText(input.getDepartmentNo())
        || !StringUtils.hasText(input.getEmail()) || input.getRole() == null) {
            throw new ProcessException("employeeId, departmentNo, email, role is required");
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
                .orElseThrow(() -> new NotFoundException("address for employee with id '" + input.getEntityId() + "' not found" ));


        if(input.getEntityId().trim().equalsIgnoreCase(foundAddress.getEntityId())
                && input.getUuid().trim().equalsIgnoreCase(foundAddress.getUuid())
                && input.getStreet().trim().equalsIgnoreCase(foundAddress.getStreet())
                && input.getCity().trim().equalsIgnoreCase(foundAddress.getCity())
                && input.getState().trim().equalsIgnoreCase(foundAddress.getState())
                && input.getZipCode() == foundAddress.getZipCode()){
            throw new ProcessException("address for employee with id: '" + input.getEntityId() +"'already exist");
        }

        input.setUuid(foundAddress.getUuid());
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
                .orElseThrow(() -> new NotFoundException("Employee with id '" + input.getEmployeeId() + "' not found"));



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

    public List<Person> employeeSearch(Boolean outsourced) {
        List<Person> personList = new ArrayList<>();

        if(outsourced){
            List<OutsourcedDto> outsourcedDtoList = outsourcedRepository.findAll()
                    .stream()
                    .map(mapperUtil::mapToOutsourcedDto)
                    .peek(outsourcedDto -> {
                        List<AddressDto> addressDtos = addressService.findAddressByEntityId(outsourcedDto.getOutsourceId());
                        outsourcedDto.setAddress(addressDtos);
                    })
                    .toList();
            personList.addAll(outsourcedDtoList);
        } else {
            List<EmployeeDto> employeeDtos = employeeRepository.findAll()
                    .stream()
                    .map(mapperUtil::mapToEmployeeDto)
                    .peek(employeeDto -> {
                        List<AddressDto> addressDtos = addressService.findAddressByEntityId(employeeDto.getEmployeeId());
                        employeeDto.setAddress(addressDtos);
                    })
                    .toList();
            personList.addAll(employeeDtos);
        }
        return personList;
    }


    public Person employeeSearchByStaffId(String id) {

        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(id);

        Outsourced outsourced = null;
        if(optionalEmployee.isEmpty()){
            outsourced = outsourcedRepository.findByOutsourceId(id)
                    .orElseThrow(() -> new NotFoundException("Employee with id '" + id + "' not found"));
        }

        if(optionalEmployee.isPresent()){
            EmployeeDto employeeDto = mapperUtil.mapToEmployeeDto(optionalEmployee.get());
            employeeDto.setAddress(addressService.findAddressByEntityId(employeeDto.getEmployeeId()));
            return employeeDto;
        }else {
            OutsourcedDto outsourcedDto = mapperUtil.mapToOutsourcedDto(outsourced);
            outsourcedDto.setAddress(addressService.findAddressByEntityId(outsourcedDto.getOutsourceId()));
            return outsourcedDto;
        }
    }

    public Connection<Outsourced> outsourcedSearch(Optional<OutsourcedSearchInput> inputSearch, DataFetchingEnvironment dataFetchingEnvironment) {
        
        if(inputSearch.isPresent()){
            List<Sort.Order> orders =  SpecUtil.buildSortOrder(inputSearch.get().getSorts());

            List<Outsourced> results = outsourcedRepository.findAll(
                  outsourcedSearchQuery.buildOutsourcedSearchParam(inputSearch.get()),
                  Sort.by(orders)
            );
            return new SimpleListConnection<>(results).get(dataFetchingEnvironment);

        }else {
            Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, OUTSOURCED_UUID);
            List<Outsourced> result = outsourcedRepository.findAll(pageable)
                    .stream().toList();

            return new SimpleListConnection<>(result).get(dataFetchingEnvironment);
        }
    }

    public EmployeePagination employeeSearch(Optional<EmployeeSearchInput> inputSearch, Integer page, Integer size, DataFetchingEnvironment dataFetchingEnvironment) {

        if(page == null || page < 1){
            page = 1;
        }

        if(size == null || size < 1){
            size = 10;
        }

        Pageable pageable = PageRequest.of(page -1, size, Sort.Direction.ASC, EMPLOYEE_UUID);


        Page<Employee> pagedEmployees = inputSearch.map(employeeSearchInput ->
                        employeeRepository
                                .findAll(employeeSearchQuery.buildEmployeeSearchParams(employeeSearchInput), pageable)
                        ).orElseGet(() -> employeeRepository.findAll(pageable));

        Connection<Employee> employeeConnection = new SimpleListConnection<>(pagedEmployees.getContent()).get(dataFetchingEnvironment);

        EmployeePagination employeePagination = new EmployeePagination();
        employeePagination.setEmployeeConnection(employeeConnection);
        employeePagination.setPage(page);
        employeePagination.setSize(size);
        employeePagination.setTotalElement(pagedEmployees.getTotalElements());
        employeePagination.setTotalPage(pagedEmployees.getTotalPages());

        return employeePagination;
    }
}
