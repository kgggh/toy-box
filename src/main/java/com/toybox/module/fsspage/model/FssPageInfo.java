package com.toybox.module.fsspage.model;

import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@ToString
public class FssPageInfo {
    private final List<Filter> filter;
    private final List<Search> search;
    private final List<Sort> sort;
    private Integer page;
    private Integer size;

    @Builder
    public FssPageInfo(FssPageInfoBuilder builder) {
        this.filter = builder.filter;
        this.search = builder.search;
        this.sort = builder.sort;
        this.page = builder.page;
        this.size = builder.size;
    }

    public List<Filter> getFilter() {
        return filter;
    }

    public List<Search> getSearch() {
        return search;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public static FssPageInfoBuilder builder() {
        return new FssPageInfoBuilder();
    }

    public static class FssPageInfoBuilder {
        private List<Filter> filter = new ArrayList<>();
        private List<Search> search = new ArrayList<>();
        private List<Sort> sort = new ArrayList<>();

        private Integer page;
        private Integer size;

        public FssPageInfoBuilder() {
        }

        public FssPageInfoBuilder filter(String field, String value) {
            this.filter.add(new Filter(field, value));
            return this;
        }

        public FssPageInfoBuilder filter(Object target) {
            if(target == null){
                return this;
            }

            for (Field f : target.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    if(f.get(target) != null) {
                        String field = f.getName();
                        String value = String.valueOf(f.get(target));
                        this.filter.add(new Filter(field, value));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
            return this;
        }

        public FssPageInfoBuilder search(String field, String value) {
            this.search.add(new Search(field, value));
            return this;
        }

        public FssPageInfoBuilder search(Object target) {
            if(target == null){
                return this;
            }

            for (Field f : target.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    add(target, f);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
            return this;
        }

        private void add(Object o, Field f) throws IllegalAccessException {
            if(f.get(o) != null) {
                String field = f.getName();
                String value = String.valueOf(f.get(o));
                this.search.add(new Search(field, value));
            }
        }

        public FssPageInfoBuilder sort(String property, SortType sortType) {
            this.sort.add(new Sort(property, sortType));
            return this;
        }

        public FssPageInfoBuilder sort(Map<String, String> sortMap) {
            if(sortMap == null) {
                return this;
            }
            if (sortMap.isEmpty()) {
                return this;
            }
            for (Map.Entry<String, String> entry : sortMap.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (k == null || v == null) {
                    continue;
                }
                this.sort.add(new Sort(k, SortType.valueOf(v)));
            }
            return this;
        }

        public FssPageInfoBuilder size(Integer size) {
            this.size = size;
            return this;
        }

        public FssPageInfoBuilder page(Integer page) {
            this.page = page;
            return this;
        }

        public FssPageInfo build(){
            return new FssPageInfo(this);
        }
    }
}
