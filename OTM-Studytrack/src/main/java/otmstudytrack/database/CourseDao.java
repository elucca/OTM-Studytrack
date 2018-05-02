package otmstudytrack.database;

import java.sql.SQLException;
import java.util.List;
import otmstudytrack.domain.data.Course;

public interface CourseDao {

    public boolean addCourse(Course course) throws SQLException;

    public Course findCourseByName(String name) throws SQLException;

    public List<Course> findAllCourses() throws SQLException;

    public List<Course> findAllCoursesByActive(int active) throws SQLException;

    public boolean removeCourse(Course course) throws SQLException;

    public boolean updateCourseActive(Course course, int active) throws SQLException;

    public int findCourseID(String name) throws SQLException;

}
