package otmstudytrack.domain;

import otmstudytrack.database.*;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

/**
 * The class which handles core program logic. Is required as a dependency by
 * the UI layer, and in turn requires instances of data access layer classes as
 * dependencies for data storage.
 */
public class StudytrackService {

    private Database db;
    private CourseDao courseDao;
    private TaskTypeDao taskDao;
    private TaskEntryDao entryDao;

    /**
     * Constructs a program logic instance.
     *
     * @param db the database dependency, representing the database the service
     * connects to
     * @param courseDao the dependency for handling storage and retrieval of the
     * Course data type
     * @param taskDao the dependency for handling storage and retrieval of the
     * TaskType data type
     * @param entryDao the dependency for handling storage and retrieval of the
     * TaskEntry data type
     */
    public StudytrackService(Database db, CourseDao courseDao, TaskTypeDao taskDao, TaskEntryDao entryDao) {
        this.db = db;
        this.courseDao = courseDao;
        this.taskDao = taskDao;
        this.entryDao = entryDao;
    }

    /**
     * Adds a new task type with the provided name and course name.
     *
     * @param task the name of the task type to be added
     * @param courseName the name of the course the task type is associated with
     * @return true if adding the task type was successful
     * @throws SQLException if a database error occurred
     */
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

    /**
     * Removes the task type with the provided name and course name.
     *
     * @param courseName the name of the task type to be removed
     * @param taskName the name of the course the task type is associated with
     * @return true if the task type was successfully removed
     * @throws SQLException if a database error occurred
     */
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

    /**
     * Returns the task type with the provided name and course name.
     *
     * @param taskName the name of the task type to be retrieved
     * @param courseName the name of the course the task type is associated with
     * @return a TaskType representation of the retrieved task type, or null if
     * it doesn't exist
     * @throws SQLException if a database error occurred
     */
    public TaskType getTaskType(String taskName, String courseName) throws SQLException {
        Course courseOfTask = courseDao.findCourseByName(courseName);
        if (courseOfTask == null) {
            return null;
        }

        int courseId = courseDao.findCourseID(courseOfTask.getName());
        return taskDao.findTaskType(taskName, courseOfTask, courseId);

    }

    /**
     * Adds a new task entry related to a a particular task of a particular
     * course of a given week. To successfully add a task entry, the course and
     * task type it is associated with must exist beforehand.
     *
     * @param courseWeek the course week the task is related to
     * @param task the task type the entry is associated with
     * @param course the course the entry is associated with
     * @param hours the number of hours spent on the entry
     * @param minutes the number of minutes spent on the entry
     * @return true if the TaskEntry was successfully added
     * @throws SQLException if a database error occurred
     */
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

    /**
     * Returns the task entry associated with the provided course, task type and
     * course week.
     *
     * @param course the name of the course the entry is associated with
     * @param taskType the name of the task type the entry is associated with
     * @param courseWeek the course week the entry is associated with
     * @return a TaskEntry representation of the desired task entry, or null if
     * it doesn't exist
     * @throws SQLException
     */
    public TaskEntry getTaskEntry(String course, String taskType, int courseWeek) throws SQLException {
        Course courseOfEntry = courseDao.findCourseByName(course);
        if (courseOfEntry == null) {
            return null;
        }
        int idOfCourse = courseDao.findCourseID(course);
        TaskType taskOfEntry = taskDao.findTaskType(taskType, courseOfEntry, idOfCourse);
        if (taskOfEntry == null) {
            return null;
        }
        int idOfTaskType = taskDao.findTaskTypeId(taskOfEntry);

        if (idOfCourse == -1 || idOfTaskType == -1) {
            return null;
        }

        return entryDao.findTaskEntry(taskOfEntry, idOfTaskType, courseWeek);
    }

    /**
     * Removes the task entry associated with the provided course, task type and
     * course week.
     *
     * @param courseWeek the course week the entry is associated with
     * @param task the name of the task type the course is associated with
     * @param course the name of the course the task is associated with
     * @return true if the task entry was successfully removed
     * @throws SQLException if a database error occurred
     */
    public boolean removeTaskEntry(int courseWeek, String task, String course) throws SQLException {
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

        entryDao.removeTaskEntry(new TaskEntry(new Date(), courseWeek, foundTaskType, Duration.ZERO), foundTaskId, courseWeek);
        return true;
    }

    /**
     * Adds a course with the given name and subject.
     *
     * @param name the name of the course
     * @param subject the subject (i.e., mathematics) of the course
     * @throws SQLException if a database error occurred
     */
    public void addCourse(String name, String subject) throws SQLException {
        courseDao.addCourse(new Course(name, subject));
    }

    /**
     * Removes the course with the given name if it exists.
     *
     * @param name the name of the course to be removed
     * @return true if the course was successfully removed
     * @throws SQLException if a database error occurred
     */
    public boolean removeCourse(String name) throws SQLException {
        return courseDao.removeCourse(new Course(name, ""));
    }

    /**
     * Returns the course with the given name if it exists.
     *
     * @param name the name of the course to be retrieved
     * @return a Course representation of the desired course, or null if it
     * doesn't exist
     * @throws SQLException if a database error occurred
     */
    public Course getCourse(String name) throws SQLException {
        return courseDao.findCourseByName(name);
    }

    /**
     * Returns a list of all courses.
     *
     * @return a list containing courses
     * @throws SQLException if a database error occurred
     */
    public List<Course> getCourses() throws SQLException {
        return courseDao.findAllCourses();
    }

    /**
     * Returns a list of all courses which are either active or inactive.
     *
     * @param active true to retrieve active courses, false to retrieve inactive
     * courses
     * @return a list of all active or inactive courses per the active parameter
     * @throws SQLException if a database error occurred
     */
    public List<Course> getCoursesByActive(boolean active) throws SQLException {
        if (active == true) {
            return courseDao.findAllCoursesByActive(1);
        } else {
            return courseDao.findAllCoursesByActive(0);
        }
    }

    /**
     * Sets the active status of a course to active or inactive. (true or false)
     *
     * @param courseName the name of the course
     * @param active the active status to be set (true or false)
     * @return true if the course's active status was successfully assigned,
     * null if the course doesn't exists
     * @throws SQLException if a database error occurred
     */
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

    /**
     * Returns the time spent on the course with the given name, which includes
     * the time spent on all existing task entries on all course weeks.
     *
     * @param course the name of the course
     * @return a Duration representation of the time spent on all task entries
     * of the course
     * @throws SQLException if a database error occurred
     */
    public Duration getTimeSpentOnCourse(String course) throws SQLException {
        List<TaskType> foundTasks = getTaskTypesOfCourse(course);
        Duration timeSpent = Duration.ZERO;

        for (TaskType task : foundTasks) {
            List<TaskEntry> entries = task.getEntries();
            for (TaskEntry entry : entries) {
                timeSpent = timeSpent.plus(entry.getTimeSpent());
            }
        }

        return timeSpent;
    }

    /**
     * Returns all task types of the course with the given name.
     *
     * @param name the name of the course
     * @return a list containing all task types of the given course
     * @throws SQLException if a database error occurred
     */
    public List<TaskType> getTaskTypesOfCourse(String name) throws SQLException {
        Course foundCourse = courseDao.findCourseByName(name);
        if (foundCourse == null) {
            return new ArrayList<>();
        }

        return foundCourse.getTaskTypes();
    }

    /**
     * Permanently removes all data from the database.
     *
     * @throws SQLException if a database error occurred
     */
    public void removeAllData() throws SQLException {
        this.db.deleteAllData();
    }

}
