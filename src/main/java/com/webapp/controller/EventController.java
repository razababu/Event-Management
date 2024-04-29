package com.webapp.controller;

import com.webapp.payload.EventDto;
import com.webapp.payload.EventWithPagination;
import com.webapp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;



    //create event
    //url- http://localhost:8080/api/events
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto)
    {
        EventDto dto = eventService.createEvent(eventDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    //get event
    //url-http://localhost:8080/api/events/find?date=2024-03-15
    @GetMapping("/find")
    public ResponseEntity<EventWithPagination> findEvents(@RequestParam LocalDate date,
                                                          @RequestParam double latitude, @RequestParam double longitude,
                                                          @RequestParam(name="pageNo",required = false,defaultValue = "0") int pageNo,
                                                          @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize
    ){
         EventWithPagination list = eventService.findEventsWithin14Days(date,latitude,longitude,pageNo,pageSize);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    }
