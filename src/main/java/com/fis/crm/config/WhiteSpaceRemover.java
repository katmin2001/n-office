package com.fis.crm.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * tamdx Trim String cho @RequestBody
 */
@JsonComponent
public class WhiteSpaceRemover extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser arg0, DeserializationContext arg1)
            throws IOException, JsonProcessingException {
        return arg0.getValueAsString().trim();
    }
}
