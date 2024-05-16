package se.lexicon;

import se.lexicon.data.UserDao;
import se.lexicon.data.db.MeetingCalendarDBConnection;
import se.lexicon.data.impl.UserDaoImpl;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.model.User;

import java.util.Optional;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        /*try {
            UserDao userDao = new UserDaoImpl(MeetingCalendarDBConnection.getConnection());
            try {
                userDao.authenticate(new User("admin", "XEfxRGkle5"));
                System.out.println("You are logged in...");
            } catch (Exception e) {
                CalendarExceptionHandler.handleException(e);
            }
        } catch (Exception e) {
            CalendarExceptionHandler.handleException(e);
        }
        //User createdUser = userDao.createUser("test1");
        //System.out.println("userInfo = " + createdUser.userInfo());

        Optional<User> userOptional = userDao.findByUsername("admin3");
        if(userOptional.isPresent()){
            System.out.println(userOptional.get().userInfo());
        }*/

        //Test hashing password:
        try {
            UserDao userDao = new UserDaoImpl(MeetingCalendarDBConnection.getConnection());

            User createdUser = userDao.createUser("admin");

            Optional<User> userOptional = userDao.findByUsername("admin");
            if (userOptional.isPresent()) {
                System.out.println(userOptional.get().userInfo());
                System.out.println("Hashed password:" + userOptional.get().getHashedPassword());
            }
        } catch (Exception e) {
            CalendarExceptionHandler.handleException(e);
        }


    }
}
