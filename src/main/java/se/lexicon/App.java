package se.lexicon;

import se.lexicon.controller.CalendarController;
import se.lexicon.dao.CalendarDao;
import se.lexicon.dao.UserDao;
import se.lexicon.dao.db.MeetingCalendarDBConnection;
import se.lexicon.dao.impl.CalendarDaoImpl;
import se.lexicon.dao.impl.UserDaoImpl;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.model.User;
import se.lexicon.view.CalendarConsoleUI;
import se.lexicon.view.CalendarView;

import java.sql.Connection;
import java.util.Optional;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

       /* Connection connection = MeetingCalendarDBConnection.getConnection();
        CalendarView view = new CalendarConsoleUI();
        UserDao userDao = new UserDaoImpl(connection);
        CalendarDao calendarDao = new CalendarDaoImpl(connection);
        CalendarController controller = new CalendarController(view, userDao, calendarDao);
        controller.run();*/

        try {
            UserDao userDao = new UserDaoImpl(MeetingCalendarDBConnection.getConnection());

            User createdUser = userDao.createUser("admin");
            System.out.println("user info: " + createdUser.userInfo());

            Optional<User> userOptional = userDao.findByUsername("admin");
            if (userOptional.isPresent()) {
                System.out.println("Hashed passcode: " + userOptional.get().getHashedPassword());
            }
        } catch (Exception e) {
            CalendarExceptionHandler.handleException(e);
        }


    }
}
