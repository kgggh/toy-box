package com.toybox.modlue.fsspage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Getter
@ToString
@NoArgsConstructor
public class Sort {
    private String property;
    private SortType sortType;

    public enum SortType {
        ASC, DESC
    }

    public Sort(String property, SortType sortType) {
        this.property = property;
        this.sortType = sortType;
    }

    public Sort(Map<String, SortType> sortMap) {
        if (sortMap.isEmpty()) {
            return;
        }
        sortMap.forEach((k,v) ->{
            this.property = k;
            this.sortType = v;
        });
    }

    public <T> List<T> sortBy(List<T> list) {
        if(this.property == null || this.sortType == null) {
            return list;
        }

        if(list.isEmpty()) {
            return list;
        }

        return compare(list);
    }

    private <T> List<T> compare(List<T> list) {
        return list.stream()
                .sorted((first, second) -> {
                    try {
                        Field field = list.get(0).getClass()
                                .getDeclaredField(this.property);
                        field.setAccessible(true);
                        String a = String.valueOf(field.get(first));
                        String b = String.valueOf(field.get(second));

                        if(this.sortType.name().equals(SortType.ASC.name())) {
                            return a.compareTo(b);
                        }
                        return b.compareTo(a);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        try {
                            Field field = list.get(0).getClass()
                                    .getSuperclass().getDeclaredField(this.property);
                            field.setAccessible(true);
                            String a = String.valueOf(field.get(first));
                            String b = String.valueOf(field.get(second));

                            if(this.sortType.name().equals(SortType.ASC.name())) {
                                return a.compareTo(b);
                            }
                            return b.compareTo(a);
                        } catch (IllegalAccessException | NoSuchFieldException ex){
                            throw new RuntimeException(ex);
                        }
                    }
                }).collect(Collectors.toList());
    }
}
