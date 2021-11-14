package com.nighthawk.csa.data.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are the CRUD methods that we can use with our database
*/
@Service
@Transactional
public class ScrumTeamSqlRepository {

    @Autowired
    private ScrumTeamJpaRepository jpa;

    public List<ScrumTeam> listAll() {
        return jpa.findAll();
    }

    public void save(ScrumTeam scrum_team) {
        jpa.save(scrum_team);
    }

    public ScrumTeam get(long id) {
        return jpa.findById(id).get();
    }

    public void delete(long id) {
        jpa.deleteById(id);
    }
}