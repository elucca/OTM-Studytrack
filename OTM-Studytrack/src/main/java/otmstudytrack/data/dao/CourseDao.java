package otmstudytrack.data.dao;

import java.sql.SQLException;
import java.util.List;
import otmstudytrack.data.Course;

public interface CourseDao {

    public void addCourse(Course course) throws SQLException;
    
    public Course findCourse(String name) throws SQLException;
    
    public List<Course> findAllCourses() throws SQLException;
    
    public void removeCourse(Course course) throws SQLException;

}
