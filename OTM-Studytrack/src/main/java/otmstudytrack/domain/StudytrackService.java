package otmstudytrack.domain;

import otmstudytrack.database.SqlTaskTypeDao;
import otmstudytrack.database.SqlCourseDao;
import otmstudytrack.database.SqlTaskEntryDao;
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

    private SqlCourseDao courseDao;
    private SqlTaskTypeDao taskDao;
    private SqlTaskEntryDao entryDao;

    public StudytrackService(SqlCourseDao courseDao, SqlTaskTypeDao taskDao, SqlTaskEntryDao entryDao) {
        this.courseDao = courseDao;
        this.taskDao = taskDao;
        this.entryDao = entryDao;
    }

    public boolean addCourse(String name) throws SQLException {
        //Doesn't add duplicates
        if (courseDao.findCourse(name) == null) {
            courseDao.addCourse(new Course(name));
            return true;
        }
        return false;
    }

    public boolean addTaskType(String task, String courseName) throws SQLException {
        //Doesn't add duplicates
        int courseId = courseDao.findCourseID(new Course(courseName));
        if (taskDao.findTaskType(task, new Course(courseName), courseId) == null) {
            taskDao.addTaskType(new TaskType(task, new Course(courseName)), courseId);
            return true;
        }
        return false;
    }

    public boolean addTaskEntry(int courseWeek, String task, String course, int hours, int minutes) throws SQLException {
        //Returns false if course or task type doesn't exist.
        Course foundCourse = courseDao.findCourse(course);
        if (foundCourse == null) {
            return false;
        }
        int foundCourseId = courseDao.findCourseID(foundCourse);

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

    public List<Course> getCourses() throws SQLException {
        return courseDao.findAllCourses();
    }

    public Duration getTimeSpentOnCourse(String course) throws SQLException {
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

    public List<TaskType> getTaskTypesOfCourse(String name) throws SQLException {
        Course foundCourse = courseDao.findCourse(name);
        if (foundCourse == null) {
            return new ArrayList<>();
        }

        return foundCourse.getTaskTypes();
    }

    public List<TaskEntry> getEntriesOfTaskType(String task, String course) throws SQLException {
        int courseId = courseDao.findCourseID(new Course(course));
        return taskDao.findTaskType(task, new Course(course), courseId).getEntries();
    }

}
