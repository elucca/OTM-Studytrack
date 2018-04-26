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

    public boolean addCourse(Course course) throws SQLException {
        if (findCourseByName(course.getName()) == null) {
            PreparedStatement stmt = db.getConn().prepareStatement("INSERT INTO Course (name, subject, active) VALUES (?, ?, ?)");
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getSubject());
            
            int active = 1;
            if (course.getActive() == false) {
                active = 0;
            }
            stmt.setInt(3, active);
            
            stmt.execute();
            stmt.close();
            return true;
        }
        return false;
    }

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
            Course foundCourse = new Course(coursesRs.getString("name"), coursesRs.getString("subject"));
            foundCourses.add(foundCourse);
        }

        coursesStmt.close();
        coursesRs.close();

        return foundCourses;
    }

    public boolean removeCourse(Course course) throws SQLException {
        int courseId = findCourseID(course);

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

}
