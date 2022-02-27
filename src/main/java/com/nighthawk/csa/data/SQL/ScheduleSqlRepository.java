package com.nighthawk.csa.data.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleSqlRepository {
    @Autowired
    private ScheduleJpaRepository jpa;

    public  List<Schedule>listAll() {
        return jpa.findAll();
    }

    public void save(Schedule schedule) {
        jpa.save(schedule);
    }

    public Schedule get(long id) {
        return jpa.findById(id).get();
    }

    public void delete(long id) {
        jpa.deleteById(id);
    }

}
