package com.toybox.module.fsspage.impl;

import com.toybox.module.fsspage.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FssPage {
    public synchronized static <T> PageInfo<T> page(FssPageInfo fssPageInfo, List<T> list) {
        List<T> result = new ArrayList<>(list);
        /* filter */
        if(!fssPageInfo.getFilter().isEmpty()) {
            List<Filter> filters = fssPageInfo.getFilter();
            for (Filter f : filters) {
                f.doFilter(result);
            }
        }

        /* search */
        if(!fssPageInfo.getSearch().isEmpty()) {
            List <Search> searches = fssPageInfo.getSearch();
            for (Search s : searches) {
                s.doSearch(result);
            }
        }

        /* sort */
        if(!fssPageInfo.getSort().isEmpty()) {
            List<Sort> sorts = fssPageInfo.getSort();
            for (Sort s : sorts) {
                result = s.sortBy(result);
            }
        }

        /* pagination */
        if(fssPageInfo.getPage() == null || fssPageInfo.getSize() == null) {
            return new PageInfo<>(result);
        }
        return new PageInfo<>(result, fssPageInfo.getPage(), fssPageInfo.getSize());

    }
}
