package otmstudytrack.database;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

public class SqlTaskEntryDao {

    private Database db;

    public SqlTaskEntryDao(Database db) {
        this.db = db;
    }

    public void addTaskEntry(TaskEntry taskEntry, int taskTypeId) throws SQLException {
        PreparedStatement entryStmt = db.getConn().prepareStatement("INSERT INTO TaskEntry (date, timespent, courseweek, tasktype_id) "
                + "VALUES (?,?,?,?)");
        entryStmt.setLong(1, taskEntry.getDate().getTime());
        //Is seconds appropriate?
        entryStmt.setLong(2, taskEntry.getTimeSpent().getSeconds());
        entryStmt.setInt(3, taskEntry.getCourseWeek());
        entryStmt.setInt(4, taskTypeId);
        entryStmt.execute();
        entryStmt.close();
    }

    public TaskEntry findTaskEntry(TaskType taskType, int taskTypeId, int courseWeek) throws SQLException {
        PreparedStatement entryStmt = db.getConn().prepareStatement("SELECT * FROM TaskEntry WHERE TaskEntry.courseweek = ? "
                + "AND TaskEntry.tasktype_id = ?");
        entryStmt.setInt(1, taskTypeId);
        entryStmt.setInt(2, courseWeek);
        ResultSet entryRs = entryStmt.executeQuery();
        entryStmt.close();

        if (entryRs.next()) {
            Date date = entryRs.getDate("date");
            Duration timeSpent = Duration.ofSeconds(entryRs.getLong("timespent"));
            entryRs.close();
            return new TaskEntry(date, courseWeek, taskType, timeSpent);
        }

        entryRs.close();

        return null;
    }

    public List<TaskEntry> findEntriesOfAType(TaskType taskType, int taskTypeId) throws SQLException {
        PreparedStatement entriesStmt = db.getConn().prepareStatement("SELECT * FROM TaskEntry WHERE TaskEntry.tasktype_id = ?");
        entriesStmt.setInt(1, taskTypeId);
        ResultSet entriesRs = entriesStmt.executeQuery();
        entriesStmt.close();

        List<TaskEntry> foundEntries = new ArrayList<>();
        while (entriesRs.next()) {
            Date date = entriesRs.getDate("date");
            int courseWeek = entriesRs.getInt("courseweek");
            Duration timeSpent = Duration.ofSeconds(entriesRs.getLong("timespent"));
            foundEntries.add(new TaskEntry(date, courseWeek, taskType, timeSpent));
        }

        entriesRs.close();

        return foundEntries;
    }

    public List<TaskEntry> findEntriesOfATypeFromCourseWeek(TaskType taskType, int taskTypeId, int courseWeek) throws SQLException {
        PreparedStatement entriesStmt = db.getConn().prepareStatement("SELECT * FROM TaskEntry WHERE TaskEntry.courseweek = ?"
                + "AND TaskEntry.tasktype_id = ?");
        entriesStmt.setInt(1, courseWeek);
        entriesStmt.setInt(2, taskTypeId);
        ResultSet entriesRs = entriesStmt.executeQuery();
        entriesStmt.close();
        
        List<TaskEntry> foundEntries = new ArrayList<>();
        while (entriesRs.next()) {
            Date date = entriesRs.getDate("date");
            Duration timeSpent = Duration.ofSeconds(entriesRs.getLong("timespent"));
            foundEntries.add(new TaskEntry(date, courseWeek, taskType, timeSpent));
        }

        entriesRs.close();

        return foundEntries;
    }

    public void removeTaskEntry(TaskEntry taskEntry, int taskTypeId) throws SQLException {
        //id integer, date integer, timeSpent integer, courseWeek integer, taskType_id integer
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskEntry WHERE date = ? AND timespent = ? AND"
                + "courseweek = ? AND tasktype_id = ?");
        removeStmt.setLong(1, taskEntry.getDate().getTime());
        removeStmt.setLong(2, taskEntry.getTimeSpent().getSeconds());
        removeStmt.setInt(3, taskEntry.getCourseWeek());
        removeStmt.setInt(4, taskTypeId);
        removeStmt.execute();
        removeStmt.close();
    }

    public void removeAllEntriesOfTaskType(int taskTypeId) throws SQLException {
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskEntry WHERE tasktype_id = ?");
        removeStmt.setInt(1, taskTypeId);
        removeStmt.execute();
        removeStmt.close();
    }

}
