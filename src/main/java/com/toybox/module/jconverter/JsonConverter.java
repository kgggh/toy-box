package com.toybox.module.jconverter;

import org.json.simple.JSONObject;

import java.util.Map;

public interface JsonConverter {
    <E> E convertFromJson(JSONObject jsonObject, Class<E> clazz);
    <E> E convertFromMap(Map<String, ?> map, Class<E> clazz);
}
