package com.zee.graphqlcourse.repository;

import com.zee.graphqlcourse.entity.Outsourced;
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
public interface OutsourcedRepository extends JpaRepository<Outsourced, UUID>
    , JpaSpecificationExecutor<Outsourced> {

    Optional<Outsourced> findByOutsourceId(String outsourceId);
    List<Outsourced> findAllByOutsourceIdIn(List<String> outsourceIds);
}
