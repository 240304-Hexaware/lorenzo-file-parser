package com.revature.repositories;

import com.revature.entity.ParsedData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ParsedDataRepository extends MongoRepository<ParsedData, String> {

}