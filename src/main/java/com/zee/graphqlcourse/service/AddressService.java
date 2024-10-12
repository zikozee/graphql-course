package com.zee.graphqlcourse.service;

import com.zee.graphqlcourse.codegen.types.AddressDto;
import com.zee.graphqlcourse.entity.Address;
import com.zee.graphqlcourse.repository.AddressRepository;
import com.zee.graphqlcourse.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 12 Oct, 2024
 */

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;

    public List<AddressDto> findAddressByEntityId(String entityId) {
        return addressRepository.findByEntityId(entityId)
                .stream()
                .map(mapperUtil::mapToAddressDto)
                .toList();
    }

    public Optional<AddressDto> findAddressByEntityIdAndUuid(String entityId, UUID uuid) {
        return addressRepository.findByEntityIdAndUuid(entityId, uuid)
                .map(mapperUtil::mapToAddressDto);
    }

    public void saveAll(List<Address> addresses) {
        addressRepository.saveAll(addresses);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

}
