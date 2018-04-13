package otmstudytrack.data.dao;

import java.util.List;
import otmstudytrack.data.Course;

public interface CourseDao {

    public void addCourse(Course course);
    
    public Course findCourse(String name);
    
    public List<Course> findAllCourses();

}
