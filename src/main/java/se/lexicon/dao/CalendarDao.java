package se.lexicon.dao;

import se.lexicon.model.Calendar;

import java.util.Collection;
import java.util.Optional;

public interface CalendarDao { //CRUD Operation

    Calendar createCalendar(String title, String username);

    Optional<Calendar> findById(int id);

    Collection<Calendar> findCalendarsByUsername(String username);

    Optional<Calendar> findByTitle(String title);

    boolean deleteCalendar(int id);
}
