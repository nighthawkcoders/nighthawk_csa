package com.nighthawk.csa.model.scrum;

import com.nighthawk.csa.model.person.Person;
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
public class ScrumSqlRepository {

    @Autowired
    private ScrumJpaRepository jpa;

    public List<Scrum> listAll() {
        return jpa.findAll();
    }

    public void save(Scrum scrum) {
        jpa.save(scrum);
    }

    public Scrum get(long id) {
        return (jpa.findById(id).isPresent())
            ? jpa.findById(id).get()
            : null;
    }

    public void delete(long id) {
        jpa.deleteById(id);
    }

    private boolean is_deleted(Person p, long id) {
        return (p != null && p.getId() == id );
    }

    public void member_deleted(long id) {
        List<Scrum> scrum_list = jpa.findAll();
        for (Scrum scrum: scrum_list) {
            boolean changed = false;
            if (is_deleted(scrum.getPrimary(), id)) {scrum.setPrimary(null); changed = true;}
            if (is_deleted(scrum.getMember1(), id)) {scrum.setMember1(null); changed = true;}
            if (is_deleted(scrum.getMember2(), id)) {scrum.setMember2(null); changed = true;}
            if (is_deleted(scrum.getMember3(), id)) {scrum.setMember3(null); changed = true;}
            if (is_deleted(scrum.getMember4(), id)) {scrum.setMember4(null); changed = true;}
            if (changed) {jpa.save(scrum);}
        }

    }
}