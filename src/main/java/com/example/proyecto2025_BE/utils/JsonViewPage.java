package com.example.proyecto2025_BE.utils;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.proyecto2025_BE.views.Views;

import java.util.List;

public class JsonViewPage<T> extends PageImpl<T> {

    public JsonViewPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public JsonViewPage(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    @JsonView(Views.Ranking.class)
    @Override
    public List<T> getContent() {
        return super.getContent();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public boolean hasNext() {
        return super.hasNext();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public boolean hasPrevious() {
        return super.hasPrevious();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public int getNumber() {
        return super.getNumber();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public int getSize() {
        return super.getSize();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public boolean isFirst() {
        return super.isFirst();
    }

    @JsonView(Views.Ranking.class)
    @Override
    public boolean isLast() {
        return super.isLast();
    }
}
