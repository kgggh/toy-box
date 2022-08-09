package com.toybox.module.fsspage.model;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
@ToString
public class Search {
    private String field;
    private String value;

    public Search(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public <T> void doSearch(List<T> list) {
        try {
            if (isEmpty(list)) {
                return;
            }
            Iterator<T> it = list.iterator();
            while(it.hasNext()){
                T item = it.next();
                Set<Field> fields = getAllFields(item);

                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.getName().equals(this.field)) {
                        if (!isContains(item, f)) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
        } catch (IllegalAccessException e){
            log.error(e.getMessage(), e);
        }
    }

    private <T> Set<Field> getAllFields(T item) {
        return ReflectionUtils.getAllFields(item.getClass());
    }

    private <T> boolean isContains(T item, Field f) throws IllegalAccessException {
        return String.valueOf(f.get(item)).toLowerCase().contains(this.value.toLowerCase());
    }

    private <T> boolean isEmpty(List<T> list) {
        return this.field == null || this.value == null || list.size() == 0;
    }
}