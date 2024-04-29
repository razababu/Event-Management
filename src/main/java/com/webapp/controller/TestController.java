package com.webapp.controller;

import com.webapp.entity.Event;
import com.webapp.payload.EventDto2;
import com.webapp.payload.EventWithPagination;
import com.webapp.repository.EventRepository;
import com.webapp.service.DistanceService;
import com.webapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private DistanceService distanceService;

      //url-http://localhost:8080/api/test/find

    @GetMapping("/find")
    public ResponseEntity<EventWithPagination> getEvents(
            @RequestParam LocalDate date,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(name = "pageNo",required = false,defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize

            ){




        String date1 = date.toString();//first date
        LocalDate date2=date;
        LocalDate lastDay=date.plusDays(14);
        String days = lastDay.toString();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Event> page= eventRepository.findEventsForDateAndNext14Days(date1,days, pageable);
        List<EventDto2> collect = page.stream().map((e) -> mapToDto(e)).collect(Collectors.toList());
        EventWithPagination e=new EventWithPagination();
        e.setList(collect);
        e.setCurrentPage(pageNo);
        e.setPageSize(pageSize);
        e.setTotalPages(page.getTotalPages());
        e.setTotalEvents(page.getTotalElements());

        return new ResponseEntity<>(e, HttpStatus.OK);

    }
    EventDto2 mapToDto(Event event){
        EventDto2 dto=new EventDto2();
        dto.setEventName(event.getEvent_name());
        dto.setCity(event.getCity());
        dto.setDate(event.getDate());
       // WeatherData weather = weatherService.getWeather(event.getCity(), event.getDate());
        //dto.setWeather(weather.getWeather());
        //dto.setDistance(distanceService.getDistance(event.getLatitude(),event.getLongitude(),latitude,longitude));
return dto;

    }
}
