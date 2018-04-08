package otmstudytrack.dao;

import java.util.ArrayList;
import java.util.List;
import otmstudytrack.data.Course;

public class FakeCourseDao implements CourseDao {
    
    private List<Course> courses;
    
    public FakeCourseDao() {
        this.courses = new ArrayList<>();
    }

    @Override
    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public Course findCourse(String name) {
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Course> findAllCourses() {
        return courses;
    }

}
