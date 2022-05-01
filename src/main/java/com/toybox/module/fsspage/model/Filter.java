package com.toybox.module.fsspage.model;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
public class Filter extends Finder{
    private String field;
    private String value;

    public Filter(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public Filter(Object t) throws IllegalAccessException {
        for (Field f : t.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if(f.get(t) != null) {
                this.field = f.getName();
                this.value = String.valueOf(f.get(t));
            }
        }
    }

    @Override
    public <T> List<T> find(List<T> list) {
        if (this.field == null || this.value == null) {
            return list;
        }
        if(list.isEmpty()) {
            return list;
        }

        try {
            List<T> result = new ArrayList<>();
            for (T t : list) {
                Field[] declaredFields = t.getClass().getDeclaredFields();
                add(result, t, declaredFields);
            }
            return result;
        } catch (IllegalAccessException e){
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private <T> void add(List<T> result, T t, Field[] declaredFields) throws IllegalAccessException {
        for (Field f : declaredFields) {
            f.setAccessible(true);
            if(exist(t, f)){
                result.add(t);
            }
        }
    }

    @Override
    protected <T> boolean exist(T t, Field f) throws IllegalAccessException {
        if (f.getName().contains(this.field)) {
            return f.get(t).equals(this.value);
        }
        return false;
    }
}
