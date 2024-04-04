package com.revature.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.entity.Field;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class SpecificationParser {
    public static Map<String, Field> parseSpec(File specFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Field> map = mapper.readValue(specFile, new TypeReference<Map<String, Field>>() {});

        Set<String> keySet = map.keySet();
        for(String s : keySet) {

        }

        System.out.println(map);
        return map;
    }
}
