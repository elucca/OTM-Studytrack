package otmstudytrack.domain;

import java.sql.Connection;
import otmstudytrack.data.dao.TaskEntryDao;
import otmstudytrack.data.dao.TaskTypeDao;
import otmstudytrack.data.dao.CourseDao;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class StudytrackService {

    private CourseDao courseDao;
    private TaskTypeDao taskDao;
    private TaskEntryDao entryDao;
    private Connection db;

    public StudytrackService(CourseDao courseDao, TaskTypeDao taskDao, TaskEntryDao entryDao, Connection db) {
        this.courseDao = courseDao;
        this.taskDao = taskDao;
        this.entryDao = entryDao;
        this.db = db;
    }

    public boolean addCourse(String name) {
        //Doesn't add duplicates
        if (courseDao.findCourse(name) == null) {
            courseDao.addCourse(new Course(name));
            return true;
        }
        return false;
    }

    public boolean addTaskType(String task, String courseName) {
        //Doesn't add duplicates 
        if (taskDao.findTaskType(new TaskType(task, new Course(courseName))) == null) {
            taskDao.addTaskType(new TaskType(task, new Course(courseName)));
            return true;
        }
        return false;
    }

    public boolean addTaskEntry(int courseWeek, String task, String course, int hours, int minutes) {
        //Returns false if course or task type doesn't exist.
        Course foundCourse = courseDao.findCourse(course);
        if (foundCourse == null) {
            return false;
        }
        TaskType foundTaskType = taskDao.findTaskType(new TaskType(task, foundCourse));
        if (foundTaskType == null) {
            return false;
        }

        Duration hoursDuration = Duration.ofHours(hours);
        Duration minutesDuration = Duration.ofMinutes(minutes);
        Duration fullDuration = hoursDuration.plus(minutesDuration);

        Date date = new Date();
        entryDao.addTaskEntry(new TaskEntry(date, courseWeek, foundTaskType, fullDuration));
        return true;
    }

    public List<Course> getCourses() {
        return courseDao.findAllCourses();
    }

    public Duration getTimeSpentOnCourse(String course) {
        List<TaskType> foundTasks = courseDao.findCourse(course).getTaskTypes();
        Duration timeSpent = Duration.ZERO;

        for (TaskType task : foundTasks) {
            List<TaskEntry> entries = task.getEntries();
            for (TaskEntry entry : entries) {
                timeSpent.plus(entry.getTimeSpent());
            }
        }

        return timeSpent;
    }

    public List<TaskType> getTaskTypesOfCourse(String name) {
        Course foundCourse = courseDao.findCourse(name);
        if (foundCourse == null) {
            return new ArrayList<>();
        }
        
        return foundCourse.getTaskTypes();
    }

    public List<TaskEntry> getEntriesOfTaskType(String task, String course) {
        return taskDao.findTaskType(new TaskType(task, new Course(course))).getEntries();
    }

}
