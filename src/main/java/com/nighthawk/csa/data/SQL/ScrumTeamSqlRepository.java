package com.nighthawk.csa.data.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
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
        List<ScrumTeam> stl = jpa.findAll();
        for (ScrumTeam st: stl) {
            boolean changed = false;
            if (is_deleted(st.getPrimary(), id)) {st.setPrimary(null); changed = true;}
            if (is_deleted(st.getMember1(), id)) {st.setMember1(null); changed = true;}
            if (is_deleted(st.getMember2(), id)) {st.setMember2(null); changed = true;}
            if (is_deleted(st.getMember3(), id)) {st.setMember3(null); changed = true;}
            if (is_deleted(st.getMember4(), id)) {st.setMember4(null); changed = true;}
            if (changed) {jpa.save(st);}
        }

    }
}