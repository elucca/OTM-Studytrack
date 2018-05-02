package otmstudytrack.database;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

/**
 * Data access object class for the TaskType data type. Connects to an SQLite
 * database.
 */
public class SqlTaskEntryDao implements TaskEntryDao {

    private Database db;

    /**
     * Constructs an SqlTaskEntryDao which uses the provided Database object for
     * database connectivity.
     *
     * @param db the Database object representing the database the dao is
     * connected to
     */
    public SqlTaskEntryDao(Database db) {
        this.db = db;
    }

    /**
     * Adds a TaskEntry to the database which will be associated with the
     * TaskType which has the provided database id. The calling class will
     * typically obtain the id from an instance of SqlTaskTypeDao. If such a
     * TaskEntry already exists, a duplicate will not be added. Instead, the
     * duration field of the provided TaskEntry will be appended to the duration
     * field of the existing TaskEntry.
     *
     * @param taskEntry the TaskEntry to be added to the database
     * @param taskTypeId the database id of the TaskType the TaskEntry is
     * associated with
     * @return true if the TaskEntry was added, false if it wasn't (but time was
     * appended to an existing entry
     * @throws SQLException if an invalid SQL statement is created
     */
    public boolean addTaskEntry(TaskEntry taskEntry, int taskTypeId) throws SQLException {
        if (findTaskEntry(taskEntry.getTaskType(), taskTypeId, taskEntry.getCourseWeek()) == null) {
            PreparedStatement entryStmt = db.getConn().prepareStatement("INSERT INTO TaskEntry (date, timespent, courseweek, tasktype_id) "
                    + "VALUES (?,?,?,?)");
            entryStmt.setLong(1, taskEntry.getDate().getTime());
            //Is seconds appropriate?
            entryStmt.setLong(2, taskEntry.getTimeSpent().getSeconds());
            entryStmt.setInt(3, taskEntry.getCourseWeek());
            entryStmt.setInt(4, taskTypeId);
            entryStmt.execute();
            entryStmt.close();
            return true;
        } else {
            appendDurationToTaskEntry(taskEntry, taskEntry.getTaskType(), taskTypeId, taskEntry.getCourseWeek());
            return false;
        }
    }

    /**
     * Retrieves a TaskEntry object from the database associated with the
     * provided TaskType which has the provided database id and the provided
     * course week. The calling class will typically obtain the id from an
     * instance of SqlTaskTypeDao.
     *
     * @param taskType the TsakType the TaskEntry to be retrieved is associated
     * with
     * @param taskTypeId the database id of the TaskType the TaskEntry to be
     * retrieved is associated with
     * @param courseWeek the courseWeek field of the taskEntry to be retrieved
     * @return the retrieved TaskEntry, or null if it was not found
     * @throws SQLException if an invalid SQL statement is created
     */
    public TaskEntry findTaskEntry(TaskType taskType, int taskTypeId, int courseWeek) throws SQLException {
        PreparedStatement entryStmt = db.getConn().prepareStatement("SELECT * FROM TaskEntry WHERE TaskEntry.courseweek = ? "
                + "AND TaskEntry.tasktype_id = ?");
        entryStmt.setInt(1, courseWeek);
        entryStmt.setInt(2, taskTypeId);
        ResultSet entryRs = entryStmt.executeQuery();

        if (entryRs.next()) {
            Date date = entryRs.getDate("date");
            Duration timeSpent = Duration.ofSeconds(entryRs.getLong("timespent"));
            entryStmt.close();
            entryRs.close();
            return new TaskEntry(date, courseWeek, taskType, timeSpent);
        }

        entryStmt.close();
        entryRs.close();

        return null;
    }

    /**
     * Retrieves all TaskEntry objects from the database associated with the
     * provided TaskType which has the provided database id. The calling class
     * will typically obtain the id from an instance of SqlTaskTypeDao.
     *
     * @param taskType the TaskType the entries to be retrieved are associated
     * with
     * @param taskTypeId the database id of the TaskType the TaskEntries to be
     * retrieved are associated with
     * @return an ArrayList of TaskEntries (of any course week) associated with
     * the provided TaskType, or an empty ArrayList if none are found
     * @throws SQLException if an invalid SQL statement is created
     */
    public List<TaskEntry> findEntriesOfAType(TaskType taskType, int taskTypeId) throws SQLException {
        PreparedStatement entriesStmt = db.getConn().prepareStatement("SELECT * FROM TaskEntry WHERE TaskEntry.tasktype_id = ?");
        entriesStmt.setInt(1, taskTypeId);
        ResultSet entriesRs = entriesStmt.executeQuery();

        List<TaskEntry> foundEntries = new ArrayList<>();
        while (entriesRs.next()) {
            Date date = entriesRs.getDate("date");
            int courseWeek = entriesRs.getInt("courseweek");
            Duration timeSpent = Duration.ofSeconds(entriesRs.getLong("timespent"));
            foundEntries.add(new TaskEntry(date, courseWeek, taskType, timeSpent));
        }

        entriesStmt.close();
        entriesRs.close();

        return foundEntries;
    }

    /**
     * Removes the provided TaskEntry associated with the given course week and
     * TaskType from the database. The calling class will typically obtain the
     * required taskTypeId from an instance of SqlTaskTypeDao.
     *
     * @param taskEntry the TaskEntry to be removed from the database
     * @param taskTypeId the database id of the TaskType the TaskEntry to be
     * removed is associated with
     * @throws SQLException if an invalid SQL statement is created
     */
    public void removeTaskEntry(TaskEntry taskEntry, int taskTypeId, int courseWeek) throws SQLException {
        //id integer, date integer, timeSpent integer, courseWeek integer, taskType_id integer
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskEntry WHERE courseWeek = ?"
                + " AND tasktype_id = ?");
        removeStmt.setInt(1, taskEntry.getCourseWeek());
        removeStmt.setInt(2, taskTypeId);
        removeStmt.execute();
        removeStmt.close();
    }

    /**
     * Removes all TaskEntries associated associated with the TaskType with the
     * given database id from the database. The calling class will typically
     * obtain the id from an instance of SqlTaskTypeDao.
     *
     * @param taskTypeId the database id of the TaskType the entries to be
     * removed are associated with
     * @throws SQLException if an invalid SQL statement is created
     */
    public void removeAllEntriesOfTaskType(int taskTypeId) throws SQLException {
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskEntry WHERE tasktype_id = ?");
        removeStmt.setInt(1, taskTypeId);
        removeStmt.execute();
        removeStmt.close();
    }

    private void appendDurationToTaskEntry(TaskEntry taskEntry, TaskType taskType, int taskTypeId, int courseWeek) throws SQLException {
        TaskEntry found = findTaskEntry(taskType, taskTypeId, courseWeek);
        Duration existingTimeSpent = found.getTimeSpent();
        Duration newTimeSpent = existingTimeSpent.plus(taskEntry.getTimeSpent());

        PreparedStatement appendStmt = db.getConn().prepareStatement("UPDATE TaskEntry SET timeSpent = ? WHERE taskType_id = ? AND courseWeek = ?");
        appendStmt.setLong(1, newTimeSpent.getSeconds());
        appendStmt.setInt(2, taskTypeId);
        appendStmt.setInt(3, courseWeek);
        appendStmt.execute();
        appendStmt.close();
    }

}
