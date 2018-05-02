package otmstudytrack.database.deprecated;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.database.CourseDao;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskType;

public class FakeCourseDao implements CourseDao {

    private List<Course> courses;
    private FakeTaskTypeDao taskDao;

    public FakeCourseDao(FakeTaskTypeDao taskDao) {
        this.courses = new ArrayList<>();
        this.taskDao = taskDao;
    }

    public List<Course> findAllCourses() {
        return courses;
    }

    @Override
    public boolean addCourse(Course course) throws SQLException {
        if (courses.contains(course)) {
            return false;
        }
        courses.add(course);
        return true;
    }

    @Override
    public Course findCourseByName(String name) throws SQLException {
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                Course found = course;
                found.addTaskTypes(taskDao.findTaskTypesOfACourse(course));
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Course> findAllCoursesByActive(int active) throws SQLException {
        List<Course> foundCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                foundCourses.add(course);
            }
        }
        return foundCourses;
    }

    @Override
    public boolean removeCourse(Course course) throws SQLException {
        if (courses.contains(course)) {
            courses.remove(course);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCourseActive(Course course, int active) throws SQLException {
        for (Course existingCourse : courses) {
            if (existingCourse.getName().equals(course.getName())) {
                if (active == 0) {
                    existingCourse.setActive(false);
                }
                if (active == 1) {
                    existingCourse.setActive(true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int findCourseID(String name) throws SQLException {
        //Fake daos return a fake id of 1
        return 1;
    }

}
