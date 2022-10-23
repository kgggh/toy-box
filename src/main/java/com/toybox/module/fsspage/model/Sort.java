package com.toybox.module.fsspage.model;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@ToString
public class Sort {
    private String property;
    private SortType sortType;

    public Sort(String property, SortType sortType) {
        this.property = property;
        this.sortType = sortType;
    }

    public <T> List<T> sortBy(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        return compare(list);
    }

    private <T> List<T> compare(List<T> list) {
        return list.stream()
                .sorted((first, second) -> {
                    Set<Field> fields = getAllFields(list);
                    AtomicInteger i = new AtomicInteger();
                    Optional<Field> optField = fields.stream()
                            .filter(f -> f.getName().equals(property))
                            .findAny();

                    fields.forEach(f -> {
                        try {
                            if (optField.isPresent()) {
                                optField.get().setAccessible(true);
                                Object firstValue = optField.get().get(first);
                                Object secondValue = optField.get().get(second);

                                if (firstValue instanceof Integer) {
                                    if (this.sortType.equals(SortType.ASC)) {
                                        i.set(((Integer) firstValue).compareTo((Integer) (secondValue)));
                                    } else {
                                        i.set(((Integer) secondValue).compareTo((Integer) (firstValue)));
                                    }
                                } else {
                                    if (this.sortType.equals(SortType.ASC)) {
                                        i.set((String.valueOf(firstValue)).compareTo(String.valueOf(secondValue)));
                                    } else {
                                        i.set((String.valueOf(secondValue)).compareTo(String.valueOf(firstValue)));
                                    }
                                }
                            }
                        } catch (IllegalAccessException e) {
                            log.error(e.getMessage(), e);
                            i.set(0);
                        }
                    });
                    return i.get();
                }).collect(Collectors.toList());
    }

    private <T> Set<Field> getAllFields(List<T> list) {
        return ReflectionUtils.getAllFields(list.get(0).getClass());
    }

    private <T> boolean isEmpty(List<T> list) {
        return this.property == null || this.sortType == null || list.size() == 0;
    }
}
