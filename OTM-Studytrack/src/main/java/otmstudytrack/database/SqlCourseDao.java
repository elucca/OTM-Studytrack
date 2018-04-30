package otmstudytrack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;

/**
 * Data access object class for the Course data type. Connects to an SQLite
 * database.
 */
public class SqlCourseDao {

    private Database db;
    private SqlTaskTypeDao taskDao;

    /**
     * Constructs an SqlCourseDao which uses the provided Database object for
     * database connectivity. Requires as a dependency an instance of a
     * SqlTaskTypeDao which must use the same Database object.
     *
     * @param db the Database object representing the database the dao is
     * connected to
     * @param taskDao the SqlTaskTypeDao dependency
     */
    public SqlCourseDao(Database db, SqlTaskTypeDao taskDao) {
        this.db = db;
        this.taskDao = taskDao;
    }

    /**
     * Adds a course to the database. If the course already exists in the
     * database (specifically, its object representation evaluates to equal), it
     * is not added.
     *
     * @param course the Course to be added to the database
     * @throws SQLException if an invalid SQL statement is created
     */
    public boolean addCourse(Course course) throws SQLException {
        if (findCourseByName(course.getName()) == null) {
            PreparedStatement stmt = db.getConn().prepareStatement("INSERT INTO Course (name, subject, active) VALUES (?, ?, ?)");
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());

            int active = 1;
            if (course.isActive() == false) {
                active = 0;
            }
            stmt.setInt(3, active);

            stmt.execute();
            stmt.close();
            return true;
        }
        return false;
    }

    /**
     * Retrieves a course from the database by name.
     *
     * @param name the name of the course to find
     * @return a Course object whose name field is equal to the provided
     * parameter
     * @throws SQLException if an invalid SQL statement is created
     */
    public Course findCourseByName(String name) throws SQLException {
        PreparedStatement courseStmt = db.getConn().prepareStatement("SELECT * FROM Course WHERE Course.name = ?");
        courseStmt.setString(1, name);
        ResultSet courseRs = courseStmt.executeQuery();

        if (courseRs.next()) {
            Course course = new Course(courseRs.getString("name"), courseRs.getString("subject"));

            //Currently defaults to active if data in db is invalid
            int active = courseRs.getInt("active");
            if (active == 0) {
                course.setActive(false);
            } else if (active == 1) {
                course.setActive(true);
            }

            course.addTaskTypes(taskDao.findTaskTypesOfACourse(course, courseRs.getInt("id")));
            courseRs.close();
            return course;
        }
        courseRs.close();
        courseStmt.close();

        return null;
    }

    /**
     * Retrieves all courses from the database.
     *
     * @return an ArrayList containing Course objects representing all existing
     * courses
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
     * Retrieves all courses from the database which have the provided active
     * status.
     *
     * @param active specifies whether to find active (1) or inactive (0)
     * courses
     * @return an ArrayList containing all courses with the provided active
     * status
     * @throws SQLException SQLException if an invalid SQL statement is created
     */
    public List<Course> findAllCoursesByActive(int active) throws SQLException {
        PreparedStatement coursesStmt = db.getConn().prepareStatement("SELECT * FROM Course WHERE Course.active = ?");
        coursesStmt.setInt(1, active);
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
     * Removes a Course from the database if a course whose object
     * representation evaluates to equal with the provided parameter exists.
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
     * Sets the active field of the provided Course to 1 (true) or 0 (false).
     *
     * @param course the course whose active-status is to be changed
     * @param active the desired value of the active field: 1 (true) or 0
     * (false)
     * @return true if the course's active status was successfully assigned
     * @throws SQLException if an invalid SQL statement is created
     */
    public boolean updateCourseActive(Course course, int active) throws SQLException {
        PreparedStatement activateStmt = db.getConn().prepareStatement("UPDATE Course SET active = ? WHERE name = ?");
        activateStmt.setInt(1, active);
        activateStmt.setString(2, course.getName());
        if (activateStmt.executeUpdate() == 0) {
            activateStmt.close();
            return false;
        } else {
            activateStmt.close();
            return true;
        }
    }

    /**
     * Retrieves the database id of the course of the given name.
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
