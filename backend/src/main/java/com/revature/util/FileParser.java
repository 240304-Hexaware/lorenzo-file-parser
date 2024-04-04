package com.revature.util;

import com.revature.entity.Field;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileParser {
    /**
     This will take in a File and read the contents character by character, appending to a string before
     returning the completed string.
     */
    public String readCompleteChars(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        StringBuilder builder = new StringBuilder();
        while(reader.ready()) {
            builder.append((char)reader.read());
        }
        return builder.toString();
    }

    /**
     * This reads the entire file at once with readAllBytes.
     * @param file
     * @return
     * @throws IOException
     */
    public String readAllBytes(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath())).intern();
    }


    /**
     * This will take a flat file and a spec map in order to create a list of strings, each representing
     * one field value from the flat file.
     * @param data
     * @param spec
     * @return
     * @throws IOException
     */
    public List<String> readStringFields(String data, Map<String, Field> spec) throws IOException {
        List<String> fieldList = new ArrayList<>();

        Set<String> fields = spec.keySet();
        for(String fieldName : fields) {
            Field field = spec.get(fieldName);
            String fieldValue = data.substring(field.getStartPos(), field.getEndPos()+1).trim();
            fieldList.add(fieldValue);
        }
        return fieldList;
    }

}