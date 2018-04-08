package logic;

import otmstudytrack.dao.CourseDao;
import otmstudytrack.dao.TaskEntryDao;
import otmstudytrack.dao.TaskTypeDao;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class TaskService {
    
    private CourseTaskManager courseTaskManager;
    private TaskEntryDao taskEntryDao;

    public TaskService(CourseTaskManager courseTaskManager, TaskEntryDao taskEntryDao) {
        this.courseTaskManager = courseTaskManager;
        this.taskEntryDao = taskEntryDao;
    }
    
    public void addCourse(String name) {
        courseTaskManager.addCourse(name);
    }
    
    public boolean addTaskType(String courseName, String task) {
        
        return false;
    }

}