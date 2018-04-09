package logic;

import java.util.List;
import otmstudytrack.dao.CourseDao;
import otmstudytrack.dao.TaskTypeDao;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class CourseTaskManager {
    //Does the way tasktypes are added and then recovered by getCourse work right?
    
    private CourseDao courseDao;
    private TaskTypeDao taskTypeDao;

    public CourseTaskManager(CourseDao courseDao, TaskTypeDao taskTypeDao) {
        this.courseDao = courseDao;
        this.taskTypeDao = taskTypeDao;
    }
    
    public void addCourse(String name){
        courseDao.addCourse(new Course(name));
    }
    
    public Course getCourse(String name) {
        //Need to handle cases where these are not found
        Course foundCourse = courseDao.findCourse(name);
        
        if (foundCourse == null) {
            return null;
        }
        
        List<TaskType> foundTasks = taskTypeDao.findTaskTypesOfACourse(foundCourse);
        foundCourse.addTaskTypes(foundTasks);
        return foundCourse;
    }
    
    public List<Course> getCourses() {
        return courseDao.findAllCourses();
    }
    
    public void addTaskType(String name, String courseName) {
        taskTypeDao.addTaskType(new TaskType(name, new Course(courseName)));
    }
    
    public TaskType getTaskType(String task, String course) {
        return taskTypeDao.findTaskType(new TaskType(task, new Course(course)));
    }
    
    public List<TaskType> getTaskTypesOfCourse(String course) {
        return taskTypeDao.findTaskTypesOfACourse(new Course(course));
    }

}