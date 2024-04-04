package com.revature.repositories;

import com.revature.entity.Specification;
import com.revature.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpecificationRepository extends MongoRepository<Specification, String> {
    Optional<Specification> findBySpecName(String name);
}