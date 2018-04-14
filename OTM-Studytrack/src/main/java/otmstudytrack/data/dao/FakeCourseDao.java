package otmstudytrack.data.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class FakeCourseDao implements CourseDao {
    
    private List<Course> courses;
    private TaskTypeDao taskDao;
    
    public FakeCourseDao(TaskTypeDao taskDao) {
        this.courses = new ArrayList<>();
        this.taskDao = taskDao;
    }

    @Override
    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public Course findCourse(String name) throws SQLException {
        Course foundCourse = null;
        
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                foundCourse = course;
            }
        }
        
        if (foundCourse == null) {
            return null;
        }
                
        List<TaskType> foundTasks = taskDao.findTaskTypesOfACourse(foundCourse);
        foundCourse.addTaskTypes(foundTasks);
        return foundCourse;
    }

    @Override
    public List<Course> findAllCourses() {
        return courses;
    }

    @Override
    public void removeCourse(Course course) throws SQLException {
        courses.remove(course);
        taskDao.removeAllTaskTypesOfCourse(course);
    }

}
