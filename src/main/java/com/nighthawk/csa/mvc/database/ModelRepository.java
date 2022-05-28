package com.nighthawk.csa.mvc.database;

import com.nighthawk.csa.mvc.database.person.Person;
import com.nighthawk.csa.mvc.database.person.PersonJpaRepository;
import com.nighthawk.csa.mvc.database.chapters.Chapter;
import com.nighthawk.csa.mvc.database.chapters.ChapterJpaRepository;
import com.nighthawk.csa.mvc.database.notes.Notes;
import com.nighthawk.csa.mvc.database.notes.NotesJpaRepository;
import com.nighthawk.csa.mvc.database.role.Role;
import com.nighthawk.csa.mvc.database.role.RoleJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are some CRUD methods that we can use with our database
*/
@Service
@Transactional
public class ModelRepository implements UserDetailsService {  // "implements" ties ModelRepo to Spring Security
    // Encapsulate many object into a single Bean (Person, Roles, and Scrum)
    @Autowired  // Inject PersonJpaRepository
    private PersonJpaRepository personJpaRepository;
    @Autowired  // Inject PersonJpaRepository
    private ChapterJpaRepository chapterJpaRepository;
    @Autowired  // Inject PersonJpaRepository
    private NotesJpaRepository notesJpaRepository;
    @Autowired  // Inject RoleJpaRepository
    private RoleJpaRepository roleJpaRepository;

    // Setup Password style for Database storing and lookup
    @Autowired  // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;
    @Bean  // Sets up password encoding style
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /* UserDetailsService Overrides and maps Person & Roles POJO into Spring Security */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personJpaRepository.findByEmail(email); // setting variable user equal to the method finding the username in the database
        if(person==null){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> { //loop through roles
            authorities.add(new SimpleGrantedAuthority(role.getName())); //create a SimpleGrantedAuthority by passed in role, adding it all to the authorities list, list of roles gets past in for spring security
        });
        return new org.springframework.security.core.userdetails.User(person.getEmail(), person.getPassword(), authorities);
    }
    //Chapter Section
    public  List<Chapter>listAllChapters() {
        return chapterJpaRepository.findAllByOrderByNameAsc();
    }

    public void saveChapter(Chapter chapter) {
        chapter.setPassword(passwordEncoder.encode(chapter.getPassword()));
        chapterJpaRepository.save(chapter);
    }

    public Chapter getChapter(long id) {
        return (chapterJpaRepository.findById(id).isPresent())
                ? chapterJpaRepository.findById(id).get()
                : null;
    }

    public void deleteChapter(long id) {
        chapterJpaRepository.deleteById(id);
    }

    // Notes section

    public List<Notes>listAllNotes() {
        return notesJpaRepository.findAllByOrderByNameAsc();
    }

    public List<Notes>listAllNotesWithId(long chapterId) {
        List<Notes> notes = notesJpaRepository.findByChapterId(chapterId);
        return notes;
    }

    public void saveNotes(Notes notes) {
//        notes.setPassword(passwordEncoder.encode(notes.getPassword()));
        notesJpaRepository.save(notes);
    }

//    public Notes getNotes(long id) {
//        return (notesJpaRepository.findById(id).isPresent())
//                ? notesJpaRepository.findById(id).get()
//                : null;
//    }

    public void deleteNotes(long id) {
        notesJpaRepository.deleteById(id);
    }
    /* Person Section */

    public  List<Person>listAll() {
        return personJpaRepository.findAllByOrderByNameAsc();
    }


    // custom query to find anything containing term in username or name ignoring case
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

    public Person getByEmail(String email) {
        return (personJpaRepository.findByEmail(email));
    }

    public void delete(long id) {
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
}