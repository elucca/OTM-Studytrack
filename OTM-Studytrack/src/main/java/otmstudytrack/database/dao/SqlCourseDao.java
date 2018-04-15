package otmstudytrack.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.database.Database;

public class SqlCourseDao implements CourseDao {
    
    private Database db;

    public SqlCourseDao(Database db) {
        this.db = db;
    }
    
    @Override
    public void addCourse(Course course) throws SQLException {
        PreparedStatement stmt = db.getConn().prepareStatement("INSERT INTO Course (name) VALUES (?)");
        stmt.setString(1, course.getName());
        stmt.execute();
        stmt.close();
    }

    @Override
    public Course findCourse(String name) throws SQLException {
        PreparedStatement stmt = db.getConn().prepareStatement("");
    }

    @Override
    public List<Course> findAllCourses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeCourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}