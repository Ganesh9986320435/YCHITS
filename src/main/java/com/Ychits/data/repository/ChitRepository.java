package com.Ychits.data.repository;

import com.Ychits.data.entity.Chit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChitRepository extends MongoRepository<Chit,String> {
}
