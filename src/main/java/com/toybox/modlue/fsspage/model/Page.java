package com.toybox.modlue.fsspage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    private List<T> data;
    private long totalCount;
    private int totalPage;
    private Integer page;
    private Integer size;

    public Page(List<T> data) {
        this.data = data;
        this.totalCount = data.size();
    }

    public Page(List<T> data, int page, int size) {
        int skipCount = page * size;
        int totalCount = data.size();

        this.data = data
                .stream()
                .skip(skipCount)
                .limit(size)
                .collect(Collectors.toList());
        this.totalPage = calculatePage(totalCount, size);
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }

    private int calculatePage(int count, int size) {
        int totalPage = count / size;

        if (count % size > 0) {
            totalPage++;
        }
        return totalPage;
    }
}
