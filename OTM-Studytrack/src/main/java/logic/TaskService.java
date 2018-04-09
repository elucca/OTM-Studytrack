package logic;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import otmstudytrack.dao.CourseDao;
import otmstudytrack.dao.TaskEntryDao;
import otmstudytrack.dao.TaskTypeDao;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class TaskService {

    private CourseTaskManager courseTaskManager;
    private EntryManager entryManager;

    public TaskService(CourseTaskManager courseTaskManager, EntryManager typeEntryManager) {
        this.courseTaskManager = courseTaskManager;
        this.entryManager = typeEntryManager;
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

        System.out.println(foundCourse == null);
        System.out.println(foundTaskType == null);
        if (foundCourse == null || foundTaskType == null) {
            return false;
        }

        Date date = new Date();
        entryManager.addTaskEntry(date, courseWeek, foundTaskType);
        return true;
    }

    public boolean addTimeToTaskEntry(String task, String course, int courseWeek, int hours, int minutes) {
        //Returns false if task entry is not found
        TaskType foundTaskType = courseTaskManager.getTaskType(task, course);
        TaskEntry foundTaskEntry = entryManager.getTaskEntry(foundTaskType, courseWeek);

        if (foundTaskType == null || foundTaskEntry == null) {
            return false;
        }

        Duration hoursDuration = Duration.ofHours(hours);
        Duration minutesDuration = Duration.ofMinutes(minutes);
        Duration fullDuration = hoursDuration.plus(minutesDuration);

        foundTaskEntry.addTimeSpent(fullDuration);

        return true;
    }
    
    public List<Course> getCourses() {
        return courseTaskManager.getCourses();
    }
    
    public Duration getTimeSpentOnCourse(String course) {
        List<TaskType> foundTasks = courseTaskManager.getTaskTypesOfCourse(course);
        Duration timeSpent = Duration.ZERO;
        
        for (TaskType task : foundTasks) {
            List<TaskEntry> entries = entryManager.getEntriesOfTaskType(task.getName(), course);
            for (TaskEntry entry : entries) {
                timeSpent.plus(entry.getTimeSpent());
            }
        }
        
        return timeSpent;
    }
    
    public List<TaskType> getTaskTypesOfCourse(String name) {
        return courseTaskManager.getTaskTypesOfCourse(name);
    }
    
    public List<TaskEntry> getEntriesOfTaskType(String task, String course) {
        return entryManager.getEntriesOfTaskType(task, course);
    }

}
