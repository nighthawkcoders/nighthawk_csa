package com.nighthawk.csa.calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventSQLRepository {

    @Autowired
    private EventJpaRepository jpa;

    public List<Event> listAll() {
        return jpa.findAll();
    }

    public void save(Event event) {
        jpa.save(event);
    }

    public Event get(long id) {
        return jpa.findById(id).get();
    }

}

