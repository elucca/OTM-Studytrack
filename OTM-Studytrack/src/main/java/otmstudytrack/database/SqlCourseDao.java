package otmstudytrack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;

public class SqlCourseDao {

    private Database db;
    private SqlTaskTypeDao taskDao;

    public SqlCourseDao(Database db, SqlTaskTypeDao taskDao) {
        this.db = db;
        this.taskDao = taskDao;
    }

    public void addCourse(Course course) throws SQLException {
        if (findCourse(course.getName()) == null) {
            PreparedStatement stmt = db.getConn().prepareStatement("INSERT INTO Course (name) VALUES (?)");
            stmt.setString(1, course.getName());
            stmt.execute();
            stmt.close();
        }
    }

    public Course findCourse(String name) throws SQLException {
        //First statement rather unnecessary atm since we already have the name and 
        //courses contain nothing else.
        PreparedStatement courseStmt = db.getConn().prepareStatement("SELECT * FROM Course WHERE Course.name = ?");
        courseStmt.setString(1, name);
        ResultSet courseRs = courseStmt.executeQuery();

        if (courseRs.next()) {
            Course course = new Course(courseRs.getString("name"));
            int courseId = courseRs.getInt("id");
            course.addTaskTypes(taskDao.findTaskTypesOfACourse(course, courseId));
            courseRs.close();
            return course;
        }

        courseRs.close();
        courseStmt.close();

        return null;
    }

    public List<Course> findAllCourses() throws SQLException {
        PreparedStatement coursesStmt = db.getConn().prepareStatement("SELECT * FROM Course");
        ResultSet coursesRs = coursesStmt.executeQuery();

        List<Course> foundCourses = new ArrayList<>();

        while (coursesRs.next()) {
            Course foundCourse = new Course(coursesRs.getString("name"));
            foundCourses.add(foundCourse);
        }

        coursesStmt.close();
        coursesRs.close();

        return foundCourses;
    }

    public void removeCourse(Course course) throws SQLException {
        int courseId = findCourseID(course);

        if (courseId != -1) {
            PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM Course WHERE Course.name = ?");
            removeStmt.setString(1, course.getName());
            removeStmt.execute();
            taskDao.removeAllTaskTypesOfCourse(courseId);
            removeStmt.close();
        }
    }

    public int findCourseID(Course course) throws SQLException {
        PreparedStatement courseStmt = db.getConn().prepareStatement("SELECT Course.id FROM Course WHERE Course.name = ?");
        courseStmt.setString(1, course.getName());
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
