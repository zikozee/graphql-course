package com.zee.graphqlcourse.repository;

import com.zee.graphqlcourse.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 06 Oct, 2024
 */

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID>
    , JpaSpecificationExecutor<Department> {

    List<Department> findDepartmentByCompanyName(String name);
}