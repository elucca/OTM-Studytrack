package otmstudytrack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;

/**
 * Data access object class for the Course data type. Connects to an SQLite database.
 */

public class SqlCourseDao {

    private Database db;
    private SqlTaskTypeDao taskDao;

    /**
     * Constructs an SqlCourseDao which uses the provided Database-object for database
     * connectivity. Requires an instance of a SqlTaskTypeDao which must connect to the
     * same database as a dependency.
     * @param db the Database object representing the database the dao is connected to
     * @param taskDao the SqlTaskTypeDao dependency
     */
    public SqlCourseDao(Database db, SqlTaskTypeDao taskDao) {
        this.db = db;
        this.taskDao = taskDao;
    }

    /**
     * Adds a course to the database. If the course already exists in the database
     * (specifically, its object representation evaluates to equal), it is not added.
     * 
     * @param course the Course to be added to the database
     * @throws SQLException if an invalid SQL statement is created
     */
    public void addCourse(Course course) throws SQLException {
        if (findCourseByName(course.getName()) == null) {
            PreparedStatement stmt = db.getConn().prepareStatement("INSERT INTO Course (name, subject, active) VALUES (?, ?, ?)");
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            stmt.setInt(2, 1);
            stmt.execute();
            stmt.close();
        }
    }

    /**
     * Retrieves a course from the database by name.
     * 
     * @param name the name of the course to find
     * @return a Course object whose name field is equal to the provided parameter
     * @throws SQLException if an invalid SQL statement is created
     */
    public Course findCourseByName(String name) throws SQLException {
        PreparedStatement courseStmt = db.getConn().prepareStatement("SELECT * FROM Course WHERE Course.name = ?");
        courseStmt.setString(1, name);
        ResultSet courseRs = courseStmt.executeQuery();

        if (courseRs.next()) {
            Course course = new Course(courseRs.getString("name"), courseRs.getString("subject"));
            int courseId = courseRs.getInt("id");
            course.addTaskTypes(taskDao.findTaskTypesOfACourse(course, courseId));
            courseRs.close();
            return course;
        }

        courseRs.close();
        courseStmt.close();

        return null;
    }

    /**
     * Retrieves all courses from the database.
     * @return an ArrayList containing Course objects representing all existing courses
     * @throws SQLException if an invalid SQL statement is created
     */
    public List<Course> findAllCourses() throws SQLException {
        PreparedStatement coursesStmt = db.getConn().prepareStatement("SELECT * FROM Course");
        ResultSet coursesRs = coursesStmt.executeQuery();

        List<Course> foundCourses = new ArrayList<>();

        while (coursesRs.next()) {
            Course foundCourse = new Course(coursesRs.getString("name"), coursesRs.getString("subject"));
            foundCourses.add(foundCourse);
        }

        coursesStmt.close();
        coursesRs.close();

        return foundCourses;
    }

    /**
     * Removes a Course from the database if a course whose object representation
     * evaluates to equal with the provided parameter exists.
     * 
     * @param course the Course to be removed from the database
     * @return true if the Course was successfully removed
     * @throws SQLException if an invalid SQL statement is created
     */
    public boolean removeCourse(Course course) throws SQLException {
        int courseId = findCourseID(course.getName());

        if (courseId != -1) {
            PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM Course WHERE Course.name = ?");
            removeStmt.setString(1, course.getName());
            removeStmt.execute();
            taskDao.removeAllTaskTypesOfCourse(courseId);
            removeStmt.close();
            return true;
        }
        
        return false;
    }
    
    /**
     * Finds the database id of the course of the given name.
     * 
     * @param name
     * @return the SQlite-generated unique integer id of the course
     * @throws SQLException if an invalid SQL statement is created
     */
    public int findCourseID(String name) throws SQLException {
        PreparedStatement courseStmt = db.getConn().prepareStatement("SELECT Course.id FROM Course WHERE Course.name = ?");
        courseStmt.setString(1, name);
        ResultSet courseRs = courseStmt.executeQuery();

        if (courseRs.next()) {
            int foundId = courseRs.getInt("id");
            courseStmt.close();
            courseRs.close();
            return foundId;
        }

        courseStmt.close();
        courseRs.close();

        return -1;
    }

}
