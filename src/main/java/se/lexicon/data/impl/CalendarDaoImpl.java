package se.lexicon.data.impl;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import se.lexicon.data.CalendarDao;
import se.lexicon.exception.MySQLException;
import se.lexicon.model.Calendar;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class CalendarDaoImpl implements CalendarDao {
    private final Connection connection;
    private    List<Calendar>  calendarList = new java.util.ArrayList<>();
    String _title;
    String _username;

    public CalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }

    //todo Implement methods
    @Override
    public Calendar createCalendar(String title, String username) {


        if(title == null ||username == null) throw new IllegalArgumentException("Title or Username is null");

        String query = "INSERT INTO calendars(username, title,CREATE_DATE) VALUES(?,?,?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query, RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2,title);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 0) {
                connection.rollback();
                throw new MySQLException("Creating calendar failed, no rows affected.");
            }



            return  new Calendar(title,username);
        } catch (SQLException e) {
            throw new MySQLException("Error occurred while creating calendar for : " + username);
        }

    }

    @Override
    public Optional<Calendar> findById(int id) {

        try (


                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM calendar WHERE id = ?")

        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    _username =  resultSet.getString(2);
                    _title = resultSet.getString(3);
                    Calendar cal = new Calendar(_title,_username);
                    return Optional.of(cal);

                } else {
                    return Optional.empty();
                }

            }
        } catch (SQLException e) {
            throw new MySQLException("Failed to get calendar by id) " + e.getMessage());
        }



    }

    @Override
    public Collection<Calendar> findCalendarsByUsername(String username) {
        calendarList = null;
        if(username == null) throw new IllegalArgumentException("Username is null");

        String query = "SELECT * FROM calendar WHERE username = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

           while (resultSet.next()) {
                _username =  resultSet.getString(2);
                _title = resultSet.getString(3);

                calendarList.add(new Calendar(_title,_username));


            }

        } catch (SQLException e) {
            throw new MySQLException("Error occurred while finding the calendar by username: " + username);
        }
        return calendarList;
    }

    @Override
    public Optional<Calendar> findByTitle(String title) {
        String query = "SELECT * FROM calendar WHERE title = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                _username =  resultSet.getString(2);
                _title = resultSet.getString(3);

               Calendar cal = new Calendar(_title,_username);
                return Optional.of(cal);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
           throw new MySQLException("Error occurred while finding the calendar by title: " + _title);
        }
    }

    @Override
    public boolean deleteCalendar(int id) {
        boolean isDeleted = false;
        int rowsDeleted;

        String deleteCalendar = "DELETE FROM calendar WHERE id = ?";
        try (
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(deleteCalendar)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, id);

            rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                isDeleted = true;

            } else {

                connection.rollback();
                throw new MySQLException("Delete operation for the calendar with id: " + id);
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new MySQLException("Error for delete calendar operation: " + e.getMessage());
        }
        return isDeleted;
    }
}
