package com.webapp.service;

import com.webapp.payload.EventDto;
import com.webapp.payload.EventWithPagination;

import java.time.LocalDate;

public interface EventService {
    public EventDto createEvent(EventDto eventDto);

 //  public List<EventDto> findEventsWithin14Days(double userLatitude, double userLongitude, LocalDate date);

    EventWithPagination findEventsWithin14Days(LocalDate date, double lat1, double lon1, int pageNo, int pageSize);
}
