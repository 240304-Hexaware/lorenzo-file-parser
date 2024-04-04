package com.revature.services;

import com.revature.entity.Specification;
import com.revature.repositories.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SpecificationService {
    private final SpecificationRepository specificationRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public SpecificationService(SpecificationRepository specificationRepository, MongoTemplate mongoTemplate){
        this.specificationRepository = specificationRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Specification findByName(String name) throws Exception {
        return specificationRepository.findBySpecName(name)
                .orElseThrow(() -> new Exception("Spec name could not be found" ));
    }

    public ResponseEntity<Specification> createSpecification(Specification specification, String userId) {
        try{
            specification.setUserId(userId);
            if(specificationRepository.findBySpecName(specification.getSpecName()).isPresent()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Specification savedSpecification = specificationRepository.save(specification);
            return new ResponseEntity<>(savedSpecification, HttpStatus.CREATED);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Specification> getAllSpecifications(String userId) {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), Specification.class);
    }
}
