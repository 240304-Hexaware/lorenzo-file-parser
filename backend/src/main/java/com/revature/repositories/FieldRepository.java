package com.revature.repositories;

import com.revature.entity.Field;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldRepository extends MongoRepository<Field, String> {

}