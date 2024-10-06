package com.zee.graphqlcourse.repository;

import com.zee.graphqlcourse.entity.Address;
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
public interface AddressRepository extends JpaRepository<Address, UUID>
    , JpaSpecificationExecutor<Address> {

    List<Address> findByEntityId(String entityId);
    Optional<Address> findByEntityIdAndUuid(String addressId, UUID uuid);
}
