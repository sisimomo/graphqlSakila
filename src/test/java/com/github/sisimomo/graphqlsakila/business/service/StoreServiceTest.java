package com.github.sisimomo.graphqlsakila.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.customer.service.AddressServiceTest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StoreRequest;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import jakarta.validation.constraints.NotNull;

@Service
public class StoreServiceTest {

  @Autowired
  private StoreService service;

  @Autowired
  private AddressServiceTest addressServiceTest;

  public StoreEntity seed() {
    StoreRequest storeCreate = StoreRequest.newBuilder().address(addressServiceTest.seed()).build();
    return service.create(storeCreate);
  }

  public StoreEntity setManager(@NotNull UUID uuid, @NotNull List<UUID> staffMemberUuids) {
    StoreEntity entity = service.getByUid(uuid);
    StoreRequest storeUpdate = StoreRequest.newBuilder().managerUuid(TestUtils.getRandomItem(staffMemberUuids))
        .address(addressServiceTest.convertToRequestDTO(entity.getAddress())).build();
    return service.update(storeUpdate, uuid);
  }

}
