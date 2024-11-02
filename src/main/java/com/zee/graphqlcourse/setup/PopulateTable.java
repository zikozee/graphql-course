package com.zee.graphqlcourse.setup;

import com.zee.graphqlcourse.codegen.types.*;
import com.zee.graphqlcourse.entity.*;
import com.zee.graphqlcourse.entity.Employee;
import com.zee.graphqlcourse.entity.Outsourced;
import com.zee.graphqlcourse.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 19 Oct, 2024
 */

@Slf4j
@ConditionalOnProperty(name = "populate.table.enabled", havingValue = "true")
@Component
@RequiredArgsConstructor
public class PopulateTable implements CommandLineRunner {
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final OutsourcedRepository outsourcedRepository;
    private final AddressRepository addressRepository;
    private final Faker faker;


    @Override
    public void run(String... args) throws Exception {

        // create 3 fictitious companies with 3 business type

        // create the 4 arm of department linked to company based on Division

        // create 200 employees with the four roles available, 40 outsourced 3 duties

        // link each of the employee/outsourced to different addresses

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Populating table Bean triggered: >>>>>>>>>>>>>>>>>>>>>>>");

        List<Company> savedCompanies= generateCompanies();
        List<Department> savedDepartments = generateDepartments(savedCompanies);
        List<Employee> savedEmployees = generateEmployees(savedDepartments);
        List<Outsourced> savedOutsourcedList = generateOutsourced(savedDepartments);

        generateAddress(savedDepartments, savedEmployees, savedOutsourcedList);
    }


    private List<Company> generateCompanies() {
        List<Company> companies = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Company company = new Company();
            company.setName(faker.company().name());
            company.setRcNumber("RC" + faker.number().randomNumber(4, true));
            company.setHeadOffice(faker.address().fullAddress());
            company.setCountry(faker.country().name());
            company.setBusinessType(BusinessType.values()[i]);

            companies.add(company);
        }

        return companyRepository.saveAll(companies);
    }

    private List<Department> generateDepartments(List<Company> savedCompanies) {

        List<Department> allDepartments = savedCompanies.stream()
                .map(company -> {
                    List<Department> departments = new ArrayList<>();

                    for (int i = 0; i < 4; i++) {
                        Department department = new Department();

                        department.setName(faker.commerce().department());
                        department.setDepartmentNo(String.valueOf(faker.number().randomNumber(6, true)));
                        department.setCompanyName(company.getName());
                        department.setRcNumber(company.getRcNumber());
                        department.setDivision(Division.values()[i]);

                        departments.add(department);
                    }
                    return departments;
                }).flatMap(List::stream).toList();


        // save one by one to catch errors
        return allDepartments.stream()
                .map(department -> {
                    try {
                        return departmentRepository.save(department);
                    }catch (Exception _) {
                        log.info("department save failed: {}", department.getName());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();

    }


    private List<Employee> generateEmployees(List<Department> savedDepartments) {
        List<Employee> allEmployees = savedDepartments.stream()
                .map(dept -> {
                    List<Employee> employees = new ArrayList<>();

                    for (int i = 0; i < 200; i++) {

                        Employee employee = new Employee();

                        employee.setName(faker.name().fullName());
                        employee.setDateOfBirth(LocalDate.now().minusYears(22 + (faker.number().numberBetween(1, 20))));
                        employee.setGender(Gender.values()[faker.number().numberBetween(0, 2)]);
                        employee.setSalary(BigDecimal.valueOf(faker.number().randomDouble(2, 30000, 100000)));
                        employee.setAge(LocalDate.now().getYear() - employee.getDateOfBirth().getYear());
                        employee.setPhone(faker.phoneNumber().phoneNumber());
                        employee.setCompanyName(dept.getCompanyName());
                        employee.setActive(true);

                        employee.setEmployeeId(faker.idNumber().valid());
                        employee.setDepartmentNo(dept.getDepartmentNo());
                        employee.setEmail(faker.internet().emailAddress());
                        employee.setRole(Role.values()[faker.number().numberBetween(0, 3)]);

                        setupGMD(i, employee);
                        employees.add(employee);
                    }
                    return employees;
                }).flatMap(List::stream).toList();

        // save one by one to catch errors
        return allEmployees.stream()
                .map(employee -> {
                    try{
                        return employeeRepository.save(employee);
                    }catch (Exception _) {
                        log.info("employee save failed: {}", employee.getName());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();
    }



    private List<Outsourced> generateOutsourced(List<Department> savedDepartments){
        List<Outsourced> allOutsourced = savedDepartments.stream()
                .map(dept -> {
                    List<Outsourced> outsourcedList = new ArrayList<>();
                    for (int i = 0; i < 40; i++) {

                        Outsourced outsourced = new Outsourced();

                        outsourced.setName(faker.name().fullName());
                        outsourced.setDateOfBirth(LocalDate.now().minusYears(22 + (faker.number().numberBetween(1, 20))));
                        outsourced.setGender(Gender.values()[faker.number().numberBetween(0, 2)]);
                        outsourced.setSalary(BigDecimal.valueOf(faker.number().randomDouble(2, 30000, 100000)));
                        outsourced.setAge(LocalDate.now().getYear() - outsourced.getDateOfBirth().getYear());
                        outsourced.setPhone(faker.phoneNumber().phoneNumber());
                        outsourced.setCompanyName(dept.getCompanyName());
                        outsourced.setActive(true);

                        outsourced.setOutsourceId(faker.idNumber().valid());
                        outsourced.setDuty(Duty.values()[faker.number().numberBetween(0,3)]);

                        outsourcedList.add(outsourced);
                    }

                    return outsourcedList;
                }).flatMap(List::stream).toList();

        // save one by one to catch errors
        return allOutsourced.stream()
                .map(outsourced -> {
                    try {
                        return outsourcedRepository.save(outsourced);
                    }catch (Exception _){
                        log.info("outsourced save failed: {}", outsourced.getName());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();
    }

    private void setupGMD(int index, Employee employee) {
        if(index ==99) employee.setRole(Role.GMD);
    }


    private void  generateAddress(List<Department> savedDepartments, List<Employee> savedEmployees,
                                  List<Outsourced> savedOutsourced) {

        List<Address> allAddresses = new ArrayList<>();

        for(Department department : savedDepartments) {
            Address address = new Address();
            address.setEntityId(department.getDepartmentNo());
            commonAddressDetails(allAddresses, address);
        }

        for(Employee employee : savedEmployees) {
            Address address = new Address();
            address.setEntityId(employee.getEmployeeId());
            commonAddressDetails(allAddresses, address);
        }

        for(Outsourced outsourced : savedOutsourced) {
            Address address = new Address();
            address.setEntityId(outsourced.getOutsourceId());
            commonAddressDetails(allAddresses, address);
        }

        allAddresses.forEach(address -> {
            try {
                addressRepository.save(address);
            }catch (Exception _) {
                log.info("address save failed: {}", address.getEntityId());
            }
        });

    }

    private void commonAddressDetails(List<Address> allAddresses, Address address) {
        address.setStreet(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setState(faker.address().state());
        address.setZipCode(isDigits(faker.address().zipCode()) ? Integer.parseInt(faker.address().zipCode()) : faker.number().numberBetween(100000, 500000));

        allAddresses.add(address);
    }

    private boolean isDigits(String str) {
        try{
            Integer.parseInt(str);
            return true;
        }catch (Exception _) {
            return false;
        }
    }
}
