package logic;

import java.util.Calendar;
import java.util.Date;
import otmstudytrack.dao.CourseDao;
import otmstudytrack.dao.TaskEntryDao;
import otmstudytrack.dao.TaskTypeDao;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class TaskService {
    
    private CourseTaskManager courseTaskManager;
    private TaskEntryDao taskEntryDao;

    public TaskService(CourseTaskManager courseTaskManager, TaskEntryDao taskEntryDao) {
        this.courseTaskManager = courseTaskManager;
        this.taskEntryDao = taskEntryDao;
    }
    
    public boolean addCourse(String name) {
        //Doesn't add duplicates
        if (courseTaskManager.getCourse(name) == null) {
            courseTaskManager.addCourse(name);
            return true;
        }
        return false;        
    }
    
    public boolean addTaskType(String task, String courseName) {
        //Courses are equal by name, dao creates new course, should work
        //Doesn't add duplicates
        if (courseTaskManager.getTaskType(task, courseName) == null) {
            courseTaskManager.addTaskType(task, courseName);
            return true;
        }
        return false;
    }
    
    public boolean addTaskEntry(int courseWeek, String task, String course) {
        //Does currently allow duplicates. Returns false if course or task type
        //doesn't exist.
        Course foundCourse = courseTaskManager.getCourse(course);
        TaskType foundTaskType = courseTaskManager.getTaskType(task, course);
        
        if (foundCourse == null || foundTaskType == null) {
            return false;
        }
        
        Date date = new Date();
        taskEntryDao.addTaskEntry(new TaskEntry(date, courseWeek, foundTaskType));
        return true;
    }
    
    public boolean addTimeToTaskEntry() {
        //Will need to decide what sort of input UI takes and how to build a Duration
        //object out of that.
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}