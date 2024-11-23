package com.zee.graphqlcourse.api;

import com.zee.graphqlcourse.codegen.DgsConstants;
import com.zee.graphqlcourse.codegen.types.*;
import com.zee.graphqlcourse.entity.Department;
import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Nov, 2024
 */

@Slf4j
@SpringBootTest
@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntityCreationTest {

    // use this strictly before security was introduced
//    @Autowired
//    GraphQlTester httpGraphQlTester;

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    Faker faker;

    static String companyName;
    static String departmentNo;
    static String rcNumber;
    static String headOffice;
    static String country;
    static String departmentName;

    static String street;
    static String city;
    static String state;
    static String zipCode;
    static String employeeId;


    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();
        companyName = faker.company().name();
        rcNumber = "RC" + faker.number().randomNumber(4, true);
        headOffice = faker.address().fullAddress();
        country = faker.country().name();
        departmentNo =String.valueOf(faker.number().randomNumber(6, true));
        departmentName = faker.commerce().department();

        street = faker.address().streetName();
        city = faker.address().city();
        state = faker.address().state();
        zipCode = faker.address().zipCode();

        employeeId = faker.idNumber().valid();
    }

    @Order(1)
    @DisplayName("Create Company Test")
    @Test
    void createCompanyTest(){

        Map<String, Object> variables = Map.of(
                "name", companyName,
                "rcNumber", rcNumber,
                "headOffice", headOffice,
                "country", country,
                "businessType", BusinessType.values()[faker.random().nextInt(BusinessType.values().length)].name()
        );

        GraphQlTester.Entity<CreationResponse, ?> entity = this.httpGraphQlTester
                .documentName("createCompanyTest")
                .variable("newCompany", variables)
                .execute()
                .path("createCompany")
                .entity(CreationResponse.class);

        log.info(entity.get().toString());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.get().getMessage()).contains("created successfully");
        Assertions.assertThat(entity.get().getSuccess()).isTrue();

    }


    @Order(2)
    @DisplayName("Create Department Test")
    @Test
    void createDepartmentTest(){

        Map<String, Object> address = Map.of(
                "entityId", departmentNo,
                "street", street,
                "city", city,
                "state", state,
                "zipCode", Integer.parseInt(zipCode)
        );

        Map<String, Object> variables = Map.of(
                "name", companyName,
                "companyName", companyName,
                "departmentNo", departmentNo,
                "rcNumber", rcNumber,
                "division", Division.values()[faker.random().nextInt(Division.values().length)],
                "address", address
        );

        GraphQlTester.Entity<CreationResponse, ?> entity = this.httpGraphQlTester
                .documentName("createDepartmentTest")
                .variable("newDept", variables)
                .execute()
                .path("createDepartment")
                .entity(CreationResponse.class);

        log.info(entity.get().toString());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.get().getSuccess()).isTrue();
        Assertions.assertThat(entity.get().getMessage()).contains("created successfully");
    }

    @Order(3)
    @DisplayName("Create Employee Test")
    @Test
    void createEmployeeTest(){
        Map<String, Object> address = Map.of(
                "entityId", departmentNo,
                "street", street,
                "city", city,
                "state", state,
                "zipCode", Integer.parseInt(zipCode)
        );

        LocalDate dob= faker.date().birthdayLocalDate(20, 30);

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", faker.name().fullName());
        variables.put("dateOfBirth", dob.toString());
        variables.put("gender", Gender.values()[faker.random().nextInt(Gender.values().length)]);
        variables.put("salary", faker.number().randomDouble(2, 50000, 80000));
        variables.put("age", LocalDate.now().getYear() - dob.getYear());
        variables.put("phone", faker.phoneNumber().phoneNumber());
        variables.put(DgsConstants.EMPLOYEEOUTSOURCEDINPUT.CompanyName, companyName);


        variables.put("employeeId", employeeId);
        variables.put("departmentNo", departmentNo);
        variables.put("email", faker.internet().emailAddress());
        variables.put("role", Role.values()[faker.random().nextInt(1, Role.values().length)]);
        variables.put("address", address);


        GraphQlTester.Entity<CreationResponse, ?> entity = this.httpGraphQlTester
                .documentName("createEmployeeTest")
                .variable("newEmployee", variables)
                .execute()
                .path(DgsConstants.MUTATION.CreateEmployee)
                .entity(CreationResponse.class);

        log.info(entity.get().toString());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.get().getSuccess()).isTrue();
        Assertions.assertThat(entity.get().getMessage()).contains("created successfully");
    }


    @Order(4)
    @DisplayName("Fetch All company Test")
    @Test
    void fetchAllCompanyTest(){
        GraphQlTester.Entity<AllCompanyResponse, ?> entity = this.httpGraphQlTester
                .documentName("fetchAllCompanyTest")
                .execute()
                .path(DgsConstants.QUERY.FetchAllCompany)
                .entity(AllCompanyResponse.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.get().getCompanies()).hasSizeGreaterThan(3);
        Assertions.assertThat(
                entity.get().getCompanies().stream()
                        .map(CompanyDto::getName)
                        .toList()
        ).contains(companyName);
    }

    @Order(5)
    @DisplayName("fetch Employee and Company")
    @Test
    void employeeWithCompanySearchTest(){
        GraphQlTester.EntityList<PersonAndEntitySearch>  searchList = this.httpGraphQlTester
                .documentName("employeeWithCompanySearchTest")
                .variable("id", employeeId)
                .execute()
                .path(DgsConstants.QUERY.EmployeeWithCompanySearch)
                .entityList(PersonAndEntitySearch.class);


        Assertions.assertThat(searchList).isNotNull();
        Assertions.assertThat(searchList.get().getFirst()).isInstanceOfAny(CompanyDto.class, EmployeeDto.class);

        EmployeeDto employeeDto = (EmployeeDto) searchList.get().getFirst();

        log.info("EmployeeDto {}", employeeDto.toString());

        Assertions.assertThat(employeeDto.getEmployeeId()).isEqualTo(employeeId);
        Assertions.assertThat(employeeDto.getCompanyName()).isEqualTo(companyName);
        Assertions.assertThat(employeeDto.getDepartmentNo()).isEqualTo(departmentNo);
    }



}
