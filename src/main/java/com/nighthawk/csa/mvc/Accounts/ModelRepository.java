package com.nighthawk.csa.mvc.Accounts;

import com.nighthawk.csa.mvc.Accounts.person.Person;
import com.nighthawk.csa.mvc.Accounts.person.PersonJpaRepository;
import com.nighthawk.csa.mvc.Accounts.role.Role;
import com.nighthawk.csa.mvc.Accounts.role.RoleJpaRepository;
import com.nighthawk.csa.mvc.Accounts.scrum.Scrum;
import com.nighthawk.csa.mvc.Accounts.scrum.ScrumJpaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are some CRUD methods that we can use with our database
*/
@Service
@Transactional
public class ModelRepository {
    // CommandLineRunner provides options to up preliminary data
    @Bean
    CommandLineRunner run(ModelRepository modelRepository) { // testing the database with role adds etc
        return args -> {
            // make sure every record added has Default encrypted password and ROLE_STUDENT
            modelRepository.defaults("123querty", "ROLE_STUDENT");

            // add privileged roles
            modelRepository.addRoleToPerson("jmort1021@gmail.com", "ROLE_TEACHER");
            modelRepository.addRoleToPerson("jmort1021@gmail.com", "ROLE_ADMIN");

            // output to console
            System.out.println(modelRepository.listAll());
            System.out.println(modelRepository.listAllRoles());
        };
    }

    // Sets up password encoding style
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired  // Inject PersonJpaRepository
    private PersonJpaRepository personJpaRepository;

    @Autowired  // Inject RoleJpaRepository
    private RoleJpaRepository roleJpaRepository;

    @Autowired  // Inject RoleJpaRepository
    private ScrumJpaRepository scrumJpaRepository;

    @Autowired  // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;

    /* Person Section */

    public  List<Person>listAll() {
        return personJpaRepository.findAll();
    }

    // custom query to find anything containing term in name or email ignoring case
    public  List<Person>listLike(String term) {
        return personJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    // custom query to find anything containing term in name or email ignoring case
    public  List<Person>listLikeNative(String term) {
        String like_term = String.format("%%%s%%",term);  // Like required % rappers
        return personJpaRepository.findByLikeTermNative(like_term);
    }

    public void save(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personJpaRepository.save(person);
    }

    public Person get(long id) {
        return (personJpaRepository.findById(id).isPresent())
                ? personJpaRepository.findById(id).get()
                : null;
    }

    public void delete(long id) {
        deleteScrumMember(id);   // make sure ID is no longer present in SCRUM Teams
        personJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Person person: listAll()) {
            if (person.getPassword() == null || person.getPassword().isEmpty() || person.getPassword().isBlank()) {
                person.setPassword(passwordEncoder.encode(password));
            }
            if (person.getRoles().isEmpty()) {
                Role role = roleJpaRepository.findByName(roleName);
                if (role != null) { // verify role
                    person.getRoles().add(role);
                }
            }
        }
    }


    /* Roles Section */

    public void saveRole(Role role) {
        Role roleObj = roleJpaRepository.findByName(role.getName());
        if (roleObj == null) {  // only add if it is not found
            roleJpaRepository.save(role);
        }
    }

    public  List<Role>listAllRoles() {
        return roleJpaRepository.findAll();
    }

    public Role findRole(String roleName) {
        return roleJpaRepository.findByName(roleName);
    }

    public void addRoleToPerson(String email, String roleName) { // by passing in the two strings you are giving the user that certain role
        Person person = personJpaRepository.findByEmail(email);
        if (person != null) {   // verify person
            Role role = roleJpaRepository.findByName(roleName);
            if (role != null) { // verify role
                boolean addRole = true;
                for (Role roleObj : person.getRoles()) {    // only add if user is missing role
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) person.getRoles().add(role);   // everything is valid for adding role
            }
        }
    }


    /* Scrum Section */

    public void saveScrum(Scrum scrum) {
        scrumJpaRepository.save(scrum);
    }

    public List<Scrum> listAllScrums() {
        return scrumJpaRepository.findAll();
    }

    public Scrum getScrum(long id) {
        return (scrumJpaRepository.findById(id).isPresent())
                ? scrumJpaRepository.findById(id).get()
                : null;
    }

    public void deleteScrum(long id) {
        scrumJpaRepository.deleteById(id);
    }

    private boolean is_deletedScrum(Person p, long id) {
        return (p != null && p.getId() == id );
    }

    public void deleteScrumMember(long id) {
        List<Scrum> scrum_list = scrumJpaRepository.findAll();
        for (Scrum scrum: scrum_list) {
            boolean changed = false;
            if (is_deletedScrum(scrum.getPrimary(), id)) {scrum.setPrimary(null); changed = true;}
            if (is_deletedScrum(scrum.getMember1(), id)) {scrum.setMember1(null); changed = true;}
            if (is_deletedScrum(scrum.getMember2(), id)) {scrum.setMember2(null); changed = true;}
            if (is_deletedScrum(scrum.getMember3(), id)) {scrum.setMember3(null); changed = true;}
            if (is_deletedScrum(scrum.getMember4(), id)) {scrum.setMember4(null); changed = true;}
            if (changed) {
                scrumJpaRepository.save(scrum);}
        }

    }

}