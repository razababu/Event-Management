package com.webapp.repository;

import com.webapp.entity.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends JpaRepository<Event,Long> {


    // List<Event> findByDate(String date);

    //Page<Event> findByDate(String date, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.date BETWEEN ?1 AND ?2 ORDER BY e.date ASC")
    Page<Event> findEventsForDateAndNext14Days(String startDate, String endDate, Pageable pageable);

}
