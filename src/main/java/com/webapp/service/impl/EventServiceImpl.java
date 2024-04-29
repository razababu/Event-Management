package com.webapp.service.impl;

import com.webapp.entity.Event;
import com.webapp.payload.*;
import com.webapp.repository.EventRepository;
import com.webapp.service.DistanceService;
import com.webapp.service.EventService;
import com.webapp.service.WeatherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {



    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DistanceService distanceService;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WeatherService weatherService;
    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = mapToEntity(eventDto);
        //save event into db
        Event save = eventRepository.save(event);
        return mapToEntityDto(save);
    }

    @Override
    public EventWithPagination findEventsWithin14Days(LocalDate date,double lat1,double lon1,int pageNo,int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Event> eventss=null;
        List<Event> event=new ArrayList<>();
        List<EventDto2> events=new ArrayList<>();
        String startDay = date.toString();
        LocalDate currentDate= date;//hold current date
        LocalDate localDate = currentDate.plusDays(14);
        String endDay = localDate.toString();
        eventss=eventRepository.findEventsForDateAndNext14Days(startDay,endDay,pageable);
        eventss.stream().map((e) -> event.add(e)).collect(Collectors.toList());



        EventDto2 e1=null;

        // events=event.stream().map((e) -> mapToEventObj(e)).collect(Collectors.toList());
        for (Event e:event){
            e1=mapToEventObj(e, lon1, lat1);
            events.add(e1);
        }

        EventWithPagination eventWithPagination=new EventWithPagination();
        eventWithPagination.setList(events);
        eventWithPagination.setCurrentPage(pageNo);
        eventWithPagination.setPageSize(pageSize);
        eventWithPagination.setTotalEvents(eventss.getTotalElements());
        eventWithPagination.setTotalPages(eventss.getTotalPages());




        return eventWithPagination;


    }
    EventDto2 mapToEventObj(Event event,double lon1,double lat1){
        EventDto2 eventDto2=new EventDto2();
        eventDto2.setEventName(event.getEvent_name());
        eventDto2.setCity(event.getCity());
        eventDto2.setDate(event.getDate());
        WeatherData weather = weatherService.getWeather(event.getCity(), event.getDate());
        double distance = distanceService.getDistance(lon1, lat1, event.getLatitude(), event.getLongitude());
        eventDto2.setDistance(distance);
        eventDto2.setWeather(weather.getWeather());
        return eventDto2;
    }


    Event mapToEntity(EventDto eventDto){
        return modelMapper.map(eventDto,Event.class);
    }
    EventDto mapToEntityDto(Event event){
        return modelMapper.map(event,EventDto.class);
    }

}



