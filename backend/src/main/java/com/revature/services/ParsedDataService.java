package com.revature.services;

import com.revature.controllers.SpecificationRequest;
import com.revature.entity.Field;
import com.revature.entity.Metadata;
import com.revature.entity.ParsedData;
import com.revature.entity.Specification;
import com.revature.repositories.MetadataRepository;
import com.revature.repositories.ParsedDataRepository;
import com.revature.repositories.SpecificationRepository;
import com.revature.util.FileParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Meta;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParsedDataService {

    private final ParsedDataRepository parsedDataRepository;
    private final SpecificationRepository specificationRepository;
    private final MetadataRepository metadataRepository;


    @Autowired
    public ParsedDataService (ParsedDataRepository parsedDataRepository, SpecificationRepository specificationRepository, MetadataRepository metadataRepository){
        this.parsedDataRepository = parsedDataRepository;
        this.specificationRepository = specificationRepository;
        this.metadataRepository = metadataRepository;
    }

//    public ParsedData parse(SpecificationRequest request) throws Exception{
//        String specificationName = request.getSpecificationName();
//        Specification specification = specificationRepository.findBySpecName(specificationName)
//                .orElseThrow(() -> new Exception("Specification not found"));
//
////        String path = "/Users/user/IdeaProjects/Lorenzo_Project/uploads/flatfile.txt";
//        String path = request.getPath();
//
//        File flatFile = new File(path); // Assuming flat file path
//
//        FileParser parser = new FileParser();
//        String data = parser.readAllBytes(flatFile);
//        Map<String, Field> specMap = specification.getSpecs(); // Assuming getSpecs() returns the specification map
//        List<String> fieldValues = parser.readStringFields(data, specMap);
//
//        JSONObject jsonObject = new JSONObject();
//        int i = 0;
//        for (String fieldName : specMap.keySet()) {
//            jsonObject.put(fieldName, fieldValues.get(i));
//            i++;
//        }
//        ParsedData dataToBeParsed = new ParsedData(jsonObject.toMap());
//        ParsedData parsedData = parsedDataRepository.save(dataToBeParsed);
//
//        Metadata bindMetadata = new Metadata(request.getUserId(), path, specificationName, parsedData.getId());
//        Metadata metadata = metadataRepository.save(bindMetadata);
//
//        parsedData.setMetadataId(metadata.getId());
//
//        return parsedDataRepository.save(dataToBeParsed);
//    }

    public List<ParsedData> parse(SpecificationRequest request) throws Exception {
        String specificationName = request.getSpecificationName();
        Specification specification = specificationRepository.findBySpecName(specificationName)
                .orElseThrow(() -> new Exception("Specification not found"));

        String path = request.getPath();
        List<String> allData = Files.readAllLines(Paths.get(path));
        List<ParsedData> parsedDataList = new ArrayList<>();
        FileParser parser = new FileParser();

        for (String data : allData) {
            Map<String, Field> specMap = specification.getSpecs();
            List<String> fieldValues = parser.readStringFields(data, specMap);

            JSONObject jsonObject = new JSONObject();
            int i = 0;
            for (String fieldName : specMap.keySet()) {
                jsonObject.put(fieldName, fieldValues.get(i));
                i++;
            }
            ParsedData dataToBeParsed = new ParsedData(jsonObject.toMap());
            ParsedData parsedData = parsedDataRepository.save(dataToBeParsed);

            Metadata bindMetadata = new Metadata(request.getUserId(), path, specificationName, parsedData.getId());
            Metadata metadata = metadataRepository.save(bindMetadata);

            parsedData.setMetadataId(metadata.getId());
            parsedDataRepository.save(parsedData);
            parsedDataList.add(parsedData);
        }
        return parsedDataList;
    }

    public ParsedData getParsedData(String parsedId) throws Exception {

        ParsedData parsedData = parsedDataRepository.findById(parsedId)
                .orElseThrow(() -> new Exception("Id not found"));
        return parsedData;
    }

    public List<ParsedData> getAllParsedData(String userId) throws Exception{
        List<Metadata> usersMetadata = metadataRepository.findByUserId(userId);     // get users metadata from id
        List<String> parsedDataId = new ArrayList<>();      // we use this to get all of the users parsedIds
        List<ParsedData> parsedData = new ArrayList<>();        // from the parsedIds, we get the parseddatas
        for(Metadata metadata: usersMetadata){
            parsedDataId.add(metadata.getParsedDataId());
        }
        for(String id: parsedDataId){
            ParsedData tempParsedData = parsedDataRepository.findById(id)
                    .orElseThrow(() -> new Exception("Id not found"));
            parsedData.add(tempParsedData);
        }
        return parsedData;
    }

    public List<ParsedData> getParsedDataFromFile(String userId, String flatFilePath) throws Exception{
        String url = "/Users/user/IdeaProjects/Lorenzo_Project/uploads/" + flatFilePath;
        List<Metadata> usersMetadata = metadataRepository.findByUserIdAndFlatFilePath(userId, url);
        List<String> parsedDataId = new ArrayList<>();
        List<ParsedData> parsedData = new ArrayList<>();
        for(Metadata metadata: usersMetadata){
            parsedDataId.add(metadata.getParsedDataId());
        }
        for(String id: parsedDataId){
            ParsedData tempParsedData = parsedDataRepository.findById(id)
                    .orElseThrow(() -> new Exception("Id not found"));
            parsedData.add(tempParsedData);
        }
        return parsedData;
    }

    public List<ParsedData> getParsedDataFromSpec(String userId, String specName) throws Exception {
        List<Metadata> usersMetadata = metadataRepository.findByUserIdAndSpecName(userId, specName);
        List<String> parsedDataId = new ArrayList<>();
        List<ParsedData> parsedData = new ArrayList<>();
        for(Metadata metadata: usersMetadata){
            parsedDataId.add(metadata.getParsedDataId());
        }
        for(String id: parsedDataId){
            ParsedData tempParsedData = parsedDataRepository.findById(id)
                    .orElseThrow(() -> new Exception("Id not found"));
            parsedData.add(tempParsedData);
        }
        return parsedData;
    }

//    public List<ParsedData> getRecordsFromSpecValue(String userId, String specField, String specValue) throws Exception{
//        List<Metadata> usersMetadata = metadataRepository.findByUserId(userId);     // get all of the users metadata
//        List<String> parsedDataId = new ArrayList<>();
//        List<ParsedData> parsedData = new ArrayList<>();
//        List<ParsedData> retval = new ArrayList<>();
//
//        for(Metadata metadata: usersMetadata){
//            parsedDataId.add(metadata.getParsedDataId());   // get all of the users parsed data id's
//        }
//
//        for(String id: parsedDataId){
//            ParsedData tempParsedData = parsedDataRepository.findById(id)
//                    .orElseThrow(() -> new Exception("Id not found"));
//            parsedData.add(tempParsedData); // get the actual parsed data from the id
//        }
//
//        for(ParsedData parsed: parsedData){ // get the parsed data where the parseddata.parseddata has a field called specField and the specFields value = specValue
//            try {
//                java.lang.reflect.Field field = parsed.getClass().getDeclaredField(specField);
//                if(field != null){
//                    field.setAccessible(true);
//                    Object temp = field.get(parsedData);
//                    if(temp == specValue){
//                        retval.add(parsed);
//                    }
//                }
//            } catch (NoSuchFieldException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return retval;
//
//    }
public List<ParsedData> getRecordsFromSpecValue(String userId, String specField, String specValue) {
    List<Metadata> usersMetadata = metadataRepository.findByUserId(userId);
    List<String> parsedDataIds = new ArrayList<>();
    List<ParsedData> retval = new ArrayList<>();

    for (Metadata metadata : usersMetadata) {
        parsedDataIds.add(metadata.getParsedDataId());
    }

    for (String id : parsedDataIds) {
        ParsedData parsedData = parsedDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ParsedData not found for id: " + id));

        Object data = parsedData.getParsedData();

        if (data instanceof Map) {
            Map<String, Object> dataMap = (Map<String, Object>) data;
            if (dataMap.containsKey(specField)) {
                Object fieldValue = dataMap.get(specField);
                if (fieldValue != null && fieldValue.equals(specValue)) {
                    retval.add(parsedData);
                }
            } else {
                System.err.println("Field '" + specField + "' not found in ParsedData's parsedData object.");
            }
        } else {
            System.err.println("ParsedData's parsedData object is not a Map.");
        }

    }

    return retval;
}

}
