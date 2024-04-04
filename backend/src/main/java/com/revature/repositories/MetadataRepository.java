package com.revature.repositories;

import com.revature.entity.Metadata;
import com.revature.entity.ParsedData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends MongoRepository<Metadata, String> {

    List<Metadata> findByUserId(String userId);
    List<Metadata> findByUserIdAndFlatFilePath(String userId, String flatFilePath);

    List<Metadata> findByUserIdAndSpecName(String userId, String specName);
}
