package com.toybox.modlue.fsspage.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class FssPageInfo {
    private Filter filter;
    private Search search;
    private Sort sort;
    private Integer size;
    private Integer page;

    private static final int DEFAULT_PAGE = 0;

    private static final int DEFAULT_SIZE = 10;

    @Builder
    public FssPageInfo(Filter filter, Search search, Sort sort, Integer size, Integer page) {
        this.filter = filter;
        this.search = search;
        this.sort = sort;
        this.size = size;
        this.page = page;
    }

    public Integer getPage() {
        return this.page == null || this.page == 0 ? DEFAULT_PAGE : this.page;
    }

    public Integer getSize() {
        return this.size == null || this.size == 0 ? DEFAULT_SIZE : this.size;
    }
}
