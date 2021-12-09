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

    public void scrum_team_delete_refs(long id) {
        List<ScrumTeam> stl = jpa.findAll();
        for (ScrumTeam st: stl) {
            boolean changed = false;
            if (st.getPrimary() != null && st.getPrimary().getId() == id) {
                st.setPrimary(null);
                changed = true;
            }
            if (st.getMember1() != null && st.getMember1().getId() == id) {
                st.setMember1(null);
                changed = true;

            }
            if (st.getMember2() != null && st.getMember2().getId() == id) {
                st.setMember2(null);
                changed = true;

            }
            if (st.getMember3() != null && st.getMember3().getId() == id) {
                st.setMember3(null);
                changed = true;

            }
            if (st.getMember4() != null && st.getMember4().getId() == id) {
                st.setMember4(null);
                changed = true;

            }
            if (changed) {jpa.save(st);}
        }

    }
}