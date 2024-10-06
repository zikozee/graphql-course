package com.zee.graphqlcourse.repository;

import com.zee.graphqlcourse.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 06 Oct, 2024
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>
    , JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByEmployeeId(String employeeId);
    List<Employee> findAllByEmployeeIdIn(List<String> employeeIds);

}
