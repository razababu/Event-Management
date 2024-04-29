package com.webapp.payload;

import java.util.List;

public class EventWithPagination {
    private List<EventDto2> events;

    private  int currentPage;

    private  int pageSize;

    private long totalEvents;
    private int totalPages;

    public List<EventDto2> getList() {
        return events;
    }

    public void setList(List<EventDto2> events) {
        this.events = events;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
