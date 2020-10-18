package com.diploma.master.repositories;

import com.diploma.master.models.entities.PriceMap;
import com.diploma.master.models.enums.PriceType;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceMapRepository extends MongoRepository<PriceMap, String> {

  Optional<PriceMap> findByTypeAndCreatedDate(PriceType type, String createdDate);
}
