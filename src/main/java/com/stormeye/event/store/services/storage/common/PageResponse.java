package com.stormeye.event.store.services.storage.common;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * The JSON for a page of data that matched the current event store API
 * @author ian@meywood.com
 */
public class PageResponse<T> {
    private final List<T> data;

    private final long pageCount;
    private final long itemCount;

    @SuppressWarnings("unused")
    public PageResponse() {
        this(new ArrayList<>(), 0, 0);
    }

    public PageResponse(final List<T> data, final long pageCount, final long itemCount) {
        this.data = data;
        this.pageCount = pageCount;
        this.itemCount = itemCount;
    }

    public PageResponse(Page<T> page) {
        this(page.getContent(), page.getTotalPages(), page.getTotalElements());
    }

    public List<T> getData() {
        return data;
    }

    public long getPageCount() {
        return pageCount;
    }

    public long getItemCount() {
        return itemCount;
    }
}
