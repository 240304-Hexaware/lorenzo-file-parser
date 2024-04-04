package com.revature.controllers;

import com.revature.entity.Field;
import com.revature.entity.Metadata;
import com.revature.entity.ParsedData;
import com.revature.entity.Specification;
import com.revature.repositories.ParsedDataRepository;
import com.revature.repositories.SpecificationRepository;
import com.revature.services.ParsedDataService;
import com.revature.services.SpecificationService;
import com.revature.util.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.query.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parse")
public class FileParsingController {

    private final ParsedDataService parsedDataService;

    public FileParsingController(ParsedDataService parsedDataService) {
        this.parsedDataService = parsedDataService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public List<ParsedData> parseFile(@RequestBody SpecificationRequest request) throws Exception {
        return parsedDataService.parse(request);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{parsedId}")
    public ParsedData getParsedData(@PathVariable String parsedId) throws Exception{
        return parsedDataService.getParsedData(parsedId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/{id}")
    public List<ParsedData> getAllParsedData(@PathVariable String id) throws Exception{
        return parsedDataService.getAllParsedData(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/file/{userId}/{flatFilePath}")
    public List<ParsedData> getParsedFromFilePath(@PathVariable String userId, @PathVariable String flatFilePath) throws Exception{
        return parsedDataService.getParsedDataFromFile(userId, flatFilePath);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/specs/{userId}/{specName}")
    public List<ParsedData> getParsedFromSpecName(@PathVariable String userId, @PathVariable String specName) throws Exception{
        return parsedDataService.getParsedDataFromSpec(userId, specName);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/key/value/{userId}/{specField}/{specValue}")
    public List<ParsedData> getRecordsFromSpecValue(@PathVariable String userId, @PathVariable String specField, @PathVariable String specValue) throws Exception{
        return parsedDataService.getRecordsFromSpecValue(userId, specField, specValue);
    }
}
