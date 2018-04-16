package otmstudytrack.database.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

@Deprecated
public class FakeCourseDao {
    
    private List<Course> courses;
    private FakeTaskTypeDao taskDao;
    
    public FakeCourseDao(FakeTaskTypeDao taskDao) {
        this.courses = new ArrayList<>();
        this.taskDao = taskDao;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

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

    public List<Course> findAllCourses() {
        return courses;
    }

    public void removeCourse(Course course) throws SQLException {
        courses.remove(course);
        taskDao.removeAllTaskTypesOfCourse(course);
    }

}
