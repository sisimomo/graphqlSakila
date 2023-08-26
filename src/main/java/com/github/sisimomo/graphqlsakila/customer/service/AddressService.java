package com.github.sisimomo.graphqlsakila.customer.service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphService;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.repository.AddressRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class AddressService extends BaseEntityGraphService<AddressEntity> {

  private final AddressRepository repository;

  public List<AddressEntity> getAllByCustomerUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM address AS a RIGHT JOIN customer AS b ON b.address_id = a.id", uuids, AddressEntity.class);
  }

  public List<AddressEntity> getAllByStaffMemberUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM address AS a RIGHT JOIN staff AS b ON b.address_id = a.id", uuids, AddressEntity.class);
  }

  public List<AddressEntity> getAllByStoreUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM address AS a RIGHT JOIN store AS b ON b.address_id = a.id", uuids, AddressEntity.class);
  }

}
