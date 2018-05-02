package otmstudytrack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskType;

/**
 * Data access object class for the TaskType data type. Connects to an SQLite
 * database.
 */
public class SqlTaskTypeDao implements TaskTypeDao {

    private Database db;
    private TaskEntryDao entryDao;

    /**
     * Constructs an SqlTaskTypeDao which uses the provided Database object for
     * database connectivity. Requires as a dependency an instance of a
     * SqlTaskEntryDao which must use the same Database object.
     *
     * @param db the Database object representing the database the dao is
     * connected to
     * @param taskDao the SqlTaskEntryDao dependency
     */
    public SqlTaskTypeDao(Database db, TaskEntryDao entryDao) {
        this.db = db;
        this.entryDao = entryDao;
    }

    /**
     * Adds a TaskType to the database which will be associated with the Course
     * which has the provided database id. The calling class will typically
     * obtain the id from an instance of SqlCourseDao.
     *
     * @param taskType the TaskType to be added to the database
     * @param courseId the database id of the course the TaskType is associated
     * with
     * @throws SQLException if an invalid SQL statement is created
     */
    public void addTaskType(TaskType taskType, int courseId) throws SQLException {
        //Currently allows adding TaskTypes to nonexistent courses. Doesn't break
        //anything, but can result in spurious database entries.
        //Should also not insert duplicates.
        PreparedStatement taskStmt = db.getConn().prepareStatement("INSERT INTO TaskType (name, course_id) VALUES (?, ?)");
        taskStmt.setString(1, taskType.getName());
        taskStmt.setInt(2, courseId);
        taskStmt.execute();
        taskStmt.close();
    }

    /**
     * Adds multiple TaskTypes which which will be associated with the course
     * which has the provided database id. The calling class will typically
     * obtain the id from an instance of SqlCourseDao.
     *
     * @param taskTypes a list containing TaskType objects
     * @param courseId the database id of the course the TaskType is associated
     * with
     * @throws SQLException if an invalid SQL statement is created
     */
    public void addTaskTypes(List<TaskType> taskTypes, int courseId) throws SQLException {
        for (TaskType taskType : taskTypes) {
            addTaskType(taskType, courseId);
        }
    }

    /**
     * Retrieves a TaskType object from the database associated with the
     * provided Course which has the provided database id. The calling class
     * will typically obtain the id from an instance of SqlCourseDao.
     *
     * @param name the name of the TaskType
     * @param course the Course object the TaskType is associated with
     * @param courseId the database id of the course the TaskType is associated
     * with
     * @return the retrieved TaskType object, or null if it was not found
     * @throws SQLException if an invalid SQL statement is created
     */
    public TaskType findTaskType(String name, Course course, int courseId) throws SQLException {
        PreparedStatement taskStmt = db.getConn().prepareStatement("SELECT * FROM TaskType WHERE TaskType.name = ?"
                + "AND TaskType.course_id = ?");
        taskStmt.setString(1, name);
        taskStmt.setInt(2, courseId);
        ResultSet taskRs = taskStmt.executeQuery();

        if (taskRs.next()) {
            String foundName = taskRs.getString("name");
            TaskType foundTaskType = new TaskType(foundName, course);
            int foundId = taskRs.getInt("id");
            foundTaskType.addEntries(entryDao.findEntriesOfAType(foundTaskType, foundId));
            taskStmt.close();
            taskRs.close();
            return foundTaskType;
        }

        taskStmt.close();
        taskRs.close();

        return null;
    }

    /**
     * Retrieves all TaskTypes associated with the provided Course and its
     * provided database id. The calling class will typically obtain the id from
     * an instance of SqlCourseDao.
     *
     * @param course the Course the TaskTypes to be retrieved are associated
     * with
     * @param courseId the database id of the course the TaskType is associated
     * with
     * @return an ArrayList containing the retrieved TaskType objects
     * @throws SQLException if an invalid SQL statement is created
     */
    public List<TaskType> findTaskTypesOfACourse(Course course, int courseId) throws SQLException {
        PreparedStatement taskStmt = db.getConn().prepareStatement("SELECT * FROM TaskType WHERE TaskType.course_id = ?");
        taskStmt.setInt(1, courseId);
        ResultSet tasksRs = taskStmt.executeQuery();

        List<TaskType> foundTasks = new ArrayList<>();

        while (tasksRs.next()) {
            foundTasks.add(new TaskType(tasksRs.getString("name"), course));
        }
        taskStmt.close();
        tasksRs.close();

        return foundTasks;
    }

    /**
     * Retrieves the database id of the provided TaskType.
     *
     * @param taskType the TaskType whose id is to be retrieved
     * @return the id of the TaskType provided, or -1 if it does not exist in
     * the database
     * @throws SQLException if an invalid SQL statement is created
     */
    public int findTaskTypeId(TaskType taskType) throws SQLException {
        PreparedStatement idStmt = db.getConn().prepareStatement("SELECT TaskType.id FROM TaskType WHERE TaskType.name = ?");
        idStmt.setString(1, taskType.getName());
        ResultSet idRs = idStmt.executeQuery();

        if (idRs.next()) {
            int foundId = idRs.getInt("id");
            idRs.close();
            idStmt.close();
            return foundId;
        }
        idStmt.close();
        idRs.close();

        return -1;
    }

    /**
     * Removes the provided TaskType associated associated with the Course with
     * the given database id from the database. The calling class will typically
     * obtain the id from an instance of SqlCourseDao.
     *
     * @param taskType the TaskType object to be removed
     * @param courseId database id of the Course the TaskType object is
     * associated with
     * @throws SQLException if an invalid SQL statement is created
     */
    public void removeTaskType(TaskType taskType, int courseId) throws SQLException {
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskType WHERE TaskType.name = ? AND TaskType.course_id = ?");
        removeStmt.setString(1, taskType.getName());
        removeStmt.setInt(2, courseId);
        removeStmt.execute();
        removeStmt.close();
    }

    /**
     * Removes all TaskTypes associated associated with the Course with the
     * given database id from the database. The calling class will typically
     * obtain the id from an instance of SqlCourseDao.
     *
     * @param courseId database id of the Course the TaskType objects are
     * associated with
     * @throws SQLException if an invalid SQL statement is created
     */
    public void removeAllTaskTypesOfCourse(int courseId) throws SQLException {
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskType WHERE TaskType.course_id = ?");
        removeStmt.setInt(1, courseId);
        removeStmt.execute();
        removeStmt.close();
    }

}
