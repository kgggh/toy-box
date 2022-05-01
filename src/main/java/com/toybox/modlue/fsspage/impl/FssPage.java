package com.toybox.modlue.fsspage.impl;

import com.toybox.modlue.fsspage.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FssPage {
    public synchronized static <T> Page<T> page(FssPageInfo fssPageInfo, List<T> list) {
        List<T> result = new ArrayList<>();
        /* filter */
        if(fssPageInfo.getFilter() != null) {
            Filter filter = fssPageInfo.getFilter();
            result = filter.find(list);
        }

        /* search */
        if(fssPageInfo.getSearch() != null) {
            Search search = fssPageInfo.getSearch();
            result = search.find(list);
        }

        /* sort */
        if(fssPageInfo.getSort() != null) {
            Sort sort = fssPageInfo.getSort();
            result = sort.sortBy(list);
        }

        /* pagination */
        int page = fssPageInfo.getPage();
        int size = fssPageInfo.getSize();

        return new Page<>(result, page, size);
    }
}
