package com.toybox.module.jconverter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JsonConverterImpl implements com.toybox.module.jconverter.JsonConverter {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    private final Logger logger = LoggerFactory.getLogger("com.toybox.module.jconverter");

    @Override
    public <E> E convertFromJson(JSONObject jsonObject, Class<E> clazz) {
        E transferModel = null;
        try {
            String jsonStr = jsonObject.toJSONString();
            logger.info("[REST   ] REQ ==> " +  jsonStr);

            transferModel = objectMapper.readValue(jsonStr, clazz);
            logger.info("[CONVERT] ==> " + transferModel);

        }catch (JsonProcessingException jp) {
            logger.error(jp.getMessage());
        }catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return transferModel;
    }

    @Override
    public <E> E convertFromMap(Map<String, ?> map, Class<E> clazz) {
        E transferModel = null;
        try {
            logger.info("[REST   ] REQ ==> " +  map);

            String mapToStr = objectMapper.writeValueAsString(map);
            transferModel = objectMapper.readValue(mapToStr, clazz);
            logger.info("[CONVERT] ==> " + transferModel);
            return transferModel;

        }catch (JsonProcessingException jp) {
            logger.error(jp.getMessage());
        }catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return transferModel;
    }
}
