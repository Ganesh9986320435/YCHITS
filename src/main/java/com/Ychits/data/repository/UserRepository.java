package com.Ychits.data.repository;

import com.Ychits.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User,String> {
    User findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByEmailOrMobile(String email,String mobile);
    @Query("{$or: [{email: ?0},{mobile: ?0}]}")
    User findByEmailOrMobile(String value);
}
