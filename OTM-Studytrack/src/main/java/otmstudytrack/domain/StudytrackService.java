package otmstudytrack.domain;

import otmstudytrack.database.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

public class StudytrackService {

    private Database db;
    private SqlCourseDao courseDao;
    private SqlTaskTypeDao taskDao;
    private SqlTaskEntryDao entryDao;

    public StudytrackService(Database db, SqlCourseDao courseDao, SqlTaskTypeDao taskDao, SqlTaskEntryDao entryDao) {
        this.db = db;
        this.courseDao = courseDao;
        this.taskDao = taskDao;
        this.entryDao = entryDao;
    }

    public boolean addTaskType(String task, String courseName) throws SQLException {
        //Doesn't add duplicates
        Course foundCourse = courseDao.findCourseByName(courseName);
        if (foundCourse != null) {
            int courseId = courseDao.findCourseID(foundCourse.getName());
            if (taskDao.findTaskType(task, foundCourse, courseId) == null) {
                taskDao.addTaskType(new TaskType(task, foundCourse), courseId);
                return true;
            }
        }

        return false;
    }

    public boolean removeTaskType(String courseName, String taskName) throws SQLException {
        Course courseOfTask = courseDao.findCourseByName(courseName);
        if (courseOfTask == null) {
            return false;
        }
        int courseId = courseDao.findCourseID(courseOfTask.getName());

        TaskType toRemove = taskDao.findTaskType(taskName, courseOfTask, courseDao.findCourseID(courseOfTask.getName()));
        if (toRemove == null) {
            return false;
        }

        //Does not actually guarantee success, make TaskTypeDao do that
        taskDao.removeTaskType(toRemove, courseId);
        return true;
    }

    public TaskType getTaskType(String taskName, String courseName) throws SQLException {
        Course courseOfTask = courseDao.findCourseByName(courseName);
        if (courseOfTask == null) {
            return null;
        }

        int courseId = courseDao.findCourseID(courseOfTask.getName());
        return taskDao.findTaskType(taskName, courseOfTask, courseId);

    }

    public boolean addTaskEntry(int courseWeek, String task, String course, int hours, int minutes) throws SQLException {
        //Returns false if course or task type doesn't exist.
        Course foundCourse = courseDao.findCourseByName(course);
        if (foundCourse == null) {
            return false;
        }
        int foundCourseId = courseDao.findCourseID(foundCourse.getName());

        TaskType foundTaskType = taskDao.findTaskType(task, foundCourse, foundCourseId);
        if (foundTaskType == null) {
            return false;
        }
        int foundTaskId = taskDao.findTaskTypeId(foundTaskType);

        Duration hoursDuration = Duration.ofHours(hours);
        Duration minutesDuration = Duration.ofMinutes(minutes);
        Duration fullDuration = hoursDuration.plus(minutesDuration);

        Date date = new Date();
        entryDao.addTaskEntry(new TaskEntry(date, courseWeek, foundTaskType, fullDuration), foundTaskId);
        return true;
    }

    public boolean removeTaskEntry(int courseWeek, String task, String course) throws SQLException {
        //This requires rework once TaskEntry is properly fixed to be weekly
        Course foundCourse = courseDao.findCourseByName(course);
        if (foundCourse == null) {
            return false;
        }
        int foundCourseId = courseDao.findCourseID(foundCourse.getName());

        TaskType foundTaskType = taskDao.findTaskType(task, foundCourse, foundCourseId);
        if (foundTaskType == null) {
            return false;
        }
        int foundTaskId = taskDao.findTaskTypeId(foundTaskType);

        //Doesn't properly check if removal was successful
        entryDao.removeTaskEntry(new TaskEntry(new Date(), courseWeek, foundTaskType, Duration.ZERO), foundTaskId);
        return true;
    }

    public void addCourse(String name, String subject) throws SQLException {
        courseDao.addCourse(new Course(name, subject));
    }
    
    public boolean removeCourse(String name) throws SQLException {
        return courseDao.removeCourse(new Course(name, ""));
    }

    public Course getCourse(String name) throws SQLException {
        return courseDao.findCourseByName(name);
    }

    public List<Course> getCourses() throws SQLException {
        return courseDao.findAllCourses();
    }

    public List<Course> getCoursesByActive(boolean active) throws SQLException {
        if (active == true) {
            return courseDao.findAllCoursesByActive(1);
        } else {
            return courseDao.findAllCoursesByActive(0);
        }
    }

    public boolean setCourseActive(String courseName, boolean active) throws SQLException {
        int activeInt = 1;
        if (active == false) {
            activeInt = 0;
        }

        Course foundCourse = courseDao.findCourseByName(courseName);
        if (foundCourse == null) {
            return false;
        }

        return courseDao.updateCourseActive(foundCourse, activeInt);
    }

    public Duration getTimeSpentOnCourse(String course) throws SQLException {
        List<TaskType> foundTasks = courseDao.findCourseByName(course).getTaskTypes();
        Duration timeSpent = Duration.ZERO;

        for (TaskType task : foundTasks) {
            List<TaskEntry> entries = task.getEntries();
            for (TaskEntry entry : entries) {
                timeSpent.plus(entry.getTimeSpent());
            }
        }

        return timeSpent;
    }

    public List<TaskType> getTaskTypesOfCourse(String name) throws SQLException {
        Course foundCourse = courseDao.findCourseByName(name);
        if (foundCourse == null) {
            return new ArrayList<>();
        }

        return foundCourse.getTaskTypes();
    }

    public List<TaskEntry> getEntriesOfTaskType(String task, String course) throws SQLException {
        Course foundCourse = courseDao.findCourseByName(course);
        int courseId = courseDao.findCourseID(foundCourse.getName());
        return taskDao.findTaskType(task, foundCourse, courseId).getEntries();
    }
    
    public void removeAllData() throws SQLException {
        this.db.deleteAllData();
    }

}
