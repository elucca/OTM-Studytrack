package logic;

import java.time.Duration;
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

    public boolean addTimeToTaskEntry(String task, String course, int courseWeek, int hours, int minutes) {
        //Returns false if task entry is not found
        TaskType foundTaskType = courseTaskManager.getTaskType(task, course);
        TaskEntry foundTaskEntry = taskEntryDao.findTaskEntry(foundTaskType, courseWeek);

        if (foundTaskType == null || foundTaskEntry == null) {
            return false;
        }

        Duration hoursDuration = Duration.ofHours(hours);
        Duration minutesDuration = Duration.ofMinutes(minutes);
        Duration fullDuration = hoursDuration.plus(minutesDuration);

        foundTaskEntry.addTimeSpent(fullDuration);

        return true;
    }

}
