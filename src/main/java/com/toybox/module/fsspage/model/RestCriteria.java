package com.toybox.module.fsspage.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class RestCriteria {
    private Integer page;
    private Integer size;
    private String sort;
    private Map<String, SortType> sortMap = new LinkedHashMap<>();

    public void setSort(String sort) {
        this.sort = sort;
        if (sort != null) {
            String[] tmp = sort.split(",");
            int index = 0;
            while(index < tmp.length) {
                String param = tmp[index];
                index++;
                String order = index < tmp.length ? tmp[index].toUpperCase() : SortType.DESC.name();
                if (order.equals(SortType.DESC.name()) || order.equals(SortType.ASC.name())) {
                    index++;
                } else {
                    order = SortType.DESC.name();
                }
                this.sortMap.putIfAbsent(param, SortType.valueOf(order));
            }
        }
    }
}
